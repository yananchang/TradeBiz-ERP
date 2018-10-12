package cn.ssh.tb.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.ssh.tb.domain.Module;
import cn.ssh.tb.domain.Role;
import cn.ssh.tb.domain.User;
import cn.ssh.tb.service.UserService;

public class AuthRealm extends AuthorizingRealm{
	
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	//授权方法   当jsp页面出现Shiro标签时, 就会执行授权方法       参数是用户的集合
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("authorizing...");
		//查出当前用户的所有权限的字符串
		User user = (User) pc.fromRealm(this.getName()).iterator().next();     //根据realm的名字去找对应的realm
		
		Set<Role> roles = user.getRoles();    //对象导航, 关联级别的数据加载
		List<String> permissions = new ArrayList<>();
		for(Role role: roles) {
			//遍历每个角色
			Set<Module> modules = role.getModules();   //得到每个角色下的模块列表
			for(Module m : modules) {
				permissions.add(m.getName());
			}
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissions);   //添加用户的权限(即用户的模块)
		return info;
	}

	//认证方法   token代表用户在界面输入的用户名和密码,  该方法是根据用户名, 在db中拿到对应的对象, 并封装返回
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("authenticating.....");
		
		//1.向下转型
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		//2.调用业务方法, 实现根据用户名查询
		String hql = "from User where userName=?";
		List<User> list = userService.find(hql, User.class, new String[]{upToken.getUsername()});
		if(list != null && list.size()>0) {
			User user = list.get(0);
			AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
			return info;   //此处如果返回, 就会立即进入到密码比较器;
		}
		
		return null;    //就会出异常
	}

}
