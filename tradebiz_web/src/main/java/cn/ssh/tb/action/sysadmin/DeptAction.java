package cn.ssh.tb.action.sysadmin;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.ssh.tb.action.BaseAction;
import cn.ssh.tb.domain.Dept;
import cn.ssh.tb.service.DeptService;
import cn.ssh.tb.utils.Page;

/**
 * Action for dept
 * @author Yanan Chang
 *
 */
public class DeptAction extends BaseAction implements ModelDriven<Dept> {
	//模型驱动
	private Dept model = new Dept();
	public Dept getModel() {
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
	
	//注入DeptService
	private DeptService deptService;
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	/**
	 * 分页查询  将数据封装到一个page分页工具类对象.   注意, page中还有分页条links(首页 上一页  下一页 末页...)
	 */
	public String list() throws Exception {
		page = deptService.findPage("from Dept", page, Dept.class, null);
		
		//设置分页的url地址
		page.setUrl("deptAction_list");  //re-enter this method to re-query the page
		
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
		Dept dept = deptService.get(Dept.class, model.getId());
		
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
		
		//将查询的结果放入值栈
		super.put("deptList", deptList);
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
		deptService.saveOrUpdate(model); //并不是所有属性都有值, 只有以上两个值
		
		//跳页面
		return "alist";
	}
	
	/**
	 * enter modify page
	 */
	public String toupdate() throws Exception {
		//1.根据id, 得到一个对象
		Dept obj = deptService.get(Dept.class, model.getId());
		
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
		Dept obj = deptService.get(Dept.class, model.getId());  //根据id, 得到一个数据库中保存的部门对象
		
		//2.设置修改了的属性 (没改的属性沿用之前的)
		obj.setParent(model.getParent());
		obj.setDeptName(model.getDeptName());
		
		deptService.saveOrUpdate(obj);
		
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
		deptService.delete(Dept.class, ids);
		
		return "alist";
	}
}
