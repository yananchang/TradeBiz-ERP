package cn.ssh.tb.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ModelDriven;

import cn.ssh.tb.action.BaseAction;
import cn.ssh.tb.domain.Dept;
import cn.ssh.tb.domain.Role;
import cn.ssh.tb.domain.User;
import cn.ssh.tb.service.DeptService;
import cn.ssh.tb.service.RoleService;
import cn.ssh.tb.service.UserService;
import cn.ssh.tb.utils.Page;

/**
 * Action for dept
 * @author Yanan Chang
 *
 */
public class UserAction extends BaseAction implements ModelDriven<User> {
	//模型驱动  model-driven
	private User model = new User();
	public User getModel() {
		return model;
	}

	//分页查询
	private Page page = new Page();
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	//注入UserService
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	private DeptService deptService;
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	/**
	 * 分页查询  将数据封装到一个page分页工具类对象.   注意, page中还有分页条links(首页 上一页  下一页 末页...)
	 */
	public String list() throws Exception {
		page = userService.findPage("from User", page, User.class, null);
		
		//设置分页的url地址
		page.setUrl("userAction_list");  //re-enter this method to re-query the page
		
		//将page对象压入到值栈的栈顶, 这样, 从值栈取出page对象时比较方便, 以后user什么的也会压栈顶, 所以可以抽取成方法
		super.push(page);
		
		
		return "list";
	}
	
	/**
	 * 查看
	 *    id=sdfsdf
	 *    model对象   在栈顶, 本身有id属性
	 */
	public String toview() throws Exception {
		//1.调用业务方法, 根据id,得到对象
		User dept = userService.get(User.class, model.getId());
		
		//放入栈顶
		super.push(dept);
		//跳转页面
		return "toview";
	}
	
	/**
	 * Enter Add page
	 */
	public String tocreate() throws Exception {
		//调用业务方法
		List<Dept> deptList = deptService.find("from Dept where state=1", Dept.class, null);
		super.put("deptList", deptList);
		
		List<User> userList = userService.find("from User where state=1", User.class, null);
		super.put("userList", userList);
		
		//跳页面
		return "tocreate";
	}
	/**
	 * save
	 * <s:select name="parent.id"
	 * <input type="text" name="deptName" value=""/>
	 * model对象可以接收以上的两个值
	 *     parent
	 *          id
	 *     deptName
	 */
	public String insert() throws Exception {
		//1.调用业务方法,直线保存
		userService.saveOrUpdate(model); 
		
		//跳页面
		return "alist";
	}
	
	/**
	 * enter modify page
	 */
	public String toupdate() throws Exception {
		//1.根据id, 得到一个对象
		User obj = userService.get(User.class, model.getId());
		
		//2.将对象放入值栈中
		super.push(obj);
		
		//3.查询父部门
		List<Dept> deptList = deptService.find("from Dept where state=1", Dept.class, null);
		
		//4.将查询的结果放入值栈, 它放在context区域中
		super.put("deptList", deptList);
		
		//5.跳页面
		return "toupdate";
	}
	
	/**
	 * update
	 */
	public String update() throws Exception {
		//调用业务    参数不能直接放model, 因为该页面只有3个值, 如果该部门对象之前有其他值, 那么就都丢掉了
		User obj = userService.get(User.class, model.getId());  //根据id, 得到一个数据库中保存的部门对象
		
		//2.设置修改了的属性 (没改的属性沿用之前的)
		obj.setDept(model.getDept());
		obj.setUserName(model.getUserName());
		obj.setState(model.getState());
		
		
		userService.saveOrUpdate(obj);
		return "alist";
	}
	
	/**
	 * delete
	 * <input type="checkbox" name="id" value="73f3fa2f-66a2-4d16-8306-78d89003031b"/>
	 * <input type="checkbox" name="id" value="4028778165861be30165861c324b0000"/>
	 * .................
	 * 
	 * model     id: 只是String类型
	 * 		问题: 具有同名框(这里是id)的一组值如何封装数据?
	 * 如果server端是string类型: 封装没问题: struts2会用逗号+空格来连接, 拼接到一起;
	 * 如果               是Integer,Float,Double,Date类型:   struts2只会保留最后一个值;
	 *      那么如何保存多个integer? 
	 *            Integer[] id;  {100,200,300}  其他数字类型同理;
	 */
	public String delete() throws Exception {
		String ids[] = model.getId().split(", ");
		
		//调用业务方法, 实现批量删除
		userService.delete(User.class, ids);
		
		return "alist";
	}
	
	/**
	 * 进入角色分配页面
	 * @return
	 * @throws Exception
	 */
	public String torole() throws Exception {
		//1.根据id, 得到用户对象
		User obj = userService.get(User.class, model.getId());
		//2.将对象保存到值栈中
		super.push(obj);
		
		//3.调用角色的业务方法,得到角色列表
		List<Role> roleList = roleService.find("from Role", Role.class, null);
		
		//4.将roleList放入值栈
		super.put("roleList", roleList);
		
		//5.得到当前用户的角色列表
		Set<Role> roleSet = obj.getRoles();
		StringBuilder sb = new StringBuilder();
		for(Role role: roleSet) {
			sb.append(role.getName()).append(",");  //管理员,船运经理,
		}
		
		//6.当前用户的角色字符串放入值栈中
		super.put("roleStr", sb.toString());
		
		return "torole";
	}
	
	/**
	 * 实现角色分配
	 * 	<input type="hidden" name="id" value="${id}"/>   是用户的id       ----model.id
	 * 
	 * <input type="checkbox" name="roleIds" value="4028a1c34ec2e5c8014ec2ebf8430001" class="input">
	 * <input type="checkbox" name="roleIds" value="4028a1cd4ee2d9d6014ee2df4c6a0001" class="input">
	 * 
	 * 
	 */
	private String role() throws Exception{
		//1.根据用户的id,得到对象
		User obj = userService.get(User.class, model.getId());
		
		//2.有哪些角色?只要遍历roleIds, 就知道了
		Set<Role> roles = new HashSet<>();     //当前选中的角色列表
		for(String id: roleIds) {
			Role role = roleService.get(Role.class, id);
			roles.add(role);      //向角色列表中添加一个新的角色
		}
		
		//3.设置用户与角色列表之间的关系
		obj.setRoles(roles);
		
		//4.保存到数据库表中
		userService.saveOrUpdate(obj);   //影响的是用户与角色中间表
		
		//5.跳页面
		return "alist";

	}
	
	private String[]  roleIds;     //保存角色的列表
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
}
