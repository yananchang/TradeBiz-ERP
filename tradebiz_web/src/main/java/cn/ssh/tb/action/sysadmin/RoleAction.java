package cn.ssh.tb.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import cn.ssh.tb.action.BaseAction;
import cn.ssh.tb.domain.Module;
import cn.ssh.tb.domain.Role;
import cn.ssh.tb.exception.SysException;
import cn.ssh.tb.service.ModuleService;
import cn.ssh.tb.service.RoleService;
import cn.ssh.tb.utils.Page;

/**
 * Action for dept
 * @author Yanan Chang
 *
 */
public class RoleAction extends BaseAction implements ModelDriven<Role> {
	//模型驱动
	private Role model = new Role();
	public Role getModel() {
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
	
	//注入RoleService
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	private ModuleService moduleService;
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	/**
	 * 分页查询  将数据封装到一个page分页工具类对象.   注意, page中还有分页条links(首页 上一页  下一页 末页...)
	 */
	public String list() throws Exception {
		page = roleService.findPage("from Role", page, Role.class, null);
		
		//设置分页的url地址
		page.setUrl("roleAction_list");  //re-enter this method to re-query the page
		
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
		try {
			//1.调用业务方法, 根据id,得到对象
			Role dept = roleService.get(Role.class, model.getId());
			
			//放入栈顶
			super.push(dept);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException("Sorry, not work this way, pls choose before further operation");
		}
		//跳转页面
		return "toview";
	}
	
	/**
	 * Enter Add page
	 */
	public String tocreate() throws Exception {
		//调用业务方法
		
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
		roleService.saveOrUpdate(model); 
		
		//跳页面
		return "alist";
	}
	
	/**
	 * enter modify page
	 */
	public String toupdate() throws Exception {
		//1.根据id, 得到一个对象
		Role obj = roleService.get(Role.class, model.getId());
		
		//2.将对象放入值栈中
		super.push(obj);
		
		//3.查询父部门
		
		//5.跳页面
		return "toupdate";
	}
	
	/**
	 * update
	 */
	public String update() throws Exception {
		//调用业务    参数不能直接放model, 因为该页面只有3个值, 如果该部门对象之前有其他值, 那么就都丢掉了
		Role obj = roleService.get(Role.class, model.getId());  //根据id, 得到一个数据库中保存的部门对象
		
		//2.设置修改了的属性 (没改的属性沿用之前的)
		obj.setName(model.getName());
		obj.setRemark(model.getRemark());
		
		roleService.saveOrUpdate(obj);
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
		roleService.delete(Role.class, ids);
		
		return "alist";
	}
	
	/**
	 * 进入模块分配页面
	 */
	public String tomodule() throws Exception {
		//1.根据角色id, 得到角色对象
		Role obj = roleService.get(Role.class, model.getId());
		
		//2.保存到值栈中
		super.push(obj);
		
		//跳页面
		return "tomodule";
	}
	
	/**
	 * 为了使用zTree树, 就要组织好zTree树所使用的json数据
	 * json数据结构如下:
	 * [{"id":"模块的id","pId":"父模块的id","name":",模块名","checked":"true|false"},
	 * {"id":"模块的id","pId":"父模块的id","name":",模块名","checked":"true|false"}
	 * ]
	 * 
	 * 常用的json插件有哪些?
	 * json-lib     fastjson     struts-json-plugin-xxx.jar   手动拼接
	 * 
	 * 如何输出?
	 * 借助于response对象输出数据
	 * 
	 */
	public String roleModuleJsonStr() throws Exception{
		//1.根据角色id, 得到角色对象
		Role role = roleService.get(Role.class, model.getId());
		
		//2.通过对象导航方式, 加载出当前角色的模块列表
		Set<Module> moduleSet = role.getModules();
		
		//3.加载出所有的模块列表
		List<Module> moduleList = moduleService.find("from Module", Module.class, null);
		int size = moduleList.size();
		//4.组织json串       不用fastjson等第三方工具的转换, 因为: 中间要判断;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(Module module:moduleList) {
			size--;
			sb.append("{\"id\":\"").append(module.getId());
			sb.append("\",\"pId\":\"").append(module.getParentId());
			sb.append("\",\"name\":\"").append(module.getName());
			sb.append("\",\"checked\":\"");
			if(moduleSet.contains(module)) {
				sb.append("true");
			}else {
				sb.append("false");
			}
			sb.append("\"}");
			
			if(size>0) {
				sb.append(",");
			}
		}
		
		sb.append("]");
		
		//5.得到response对象
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		//6.使用response对象输出json串
		response.getWriter().write(sb.toString());
		
		//7.返回NONE
		return NONE;
	}
	
	/**
	 * 保存当前角色的模块列表
	 * <input type="hidden" name="id" value="${id}"/>
	   <input type="hidden" id="moduleIds" name="moduleIds" value="" />
	 */
	public String module() throws Exception {
		//1.哪个角色?
		Role role = roleService.get(Role.class, model.getId());
		//2.选中的模块有哪些?
		String[] ids = moduleIds.split(",");
		
		//加载出这些模块列表
		Set<Module> moduleSet = new HashSet<>();
		if(ids != null && ids.length>0) {
			for(String id:ids) {
				moduleSet.add(moduleService.get(Module.class, id));//添加选中的模块, 放到模块列表中
			}
		}
		
		//3.实现角色分配新的模块
		role.setModules(moduleSet);
		//4.保存结果
		roleService.saveOrUpdate(role);
		//5.跳页面
		return "alist";
	}
	
	private String moduleIds;
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}
	
	
	
	
	
	
	
	
	
	
	
}
