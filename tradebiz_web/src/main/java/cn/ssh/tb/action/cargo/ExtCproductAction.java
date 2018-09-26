package cn.ssh.tb.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.ssh.tb.action.BaseAction;
import cn.ssh.tb.domain.ExtCproduct;
import cn.ssh.tb.domain.Factory;
import cn.ssh.tb.service.ExtCproductService;
import cn.ssh.tb.service.FactoryService;
import cn.ssh.tb.utils.Page;

/**
 * @author Yanan Chang
 *
 */
public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct> {
	//模型驱动
	private ExtCproduct model = new ExtCproduct();
	public ExtCproduct getModel() {
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
	
	//注入ExtCproductService
	private ExtCproductService extCproductService;
	public void setExtCproductService(ExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}
	
	private FactoryService factoryService;
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}
	
	
	/**
	 * Enter Add page
	 */
	public String tocreate() throws Exception {
		//1.调用业务方法, 查询出生产附件的厂家
		String hql = "from Factory where ctype='附件' and state=1";
		List<Factory> factoryList = factoryService.find(hql, Factory.class, null);			
		
		super.put("factoryList", factoryList); //放入值栈中
		//2.查询出当前货物下的附件列表
		extCproductService.findPage("from ExtCproduct where contractProduct.id=?", page, ExtCproduct.class, 
				new String[] {model.getContractProduct().getId()});
		
		//設置page的url
		page.setUrl("extCproductAction_tocreate");
		
		//将page放入栈顶
		super.push(page);
		
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
		extCproductService.saveOrUpdate(model); 
		
		//跳页面
		return tocreate();
	}
	
	/**
	 * enter modify page
	 */
	public String toupdate() throws Exception {
		//1.根据id, 得到一个对象
		ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());
		
		//2.将对象放入值栈中
		super.push(obj);
		
		//3.加载生产厂家列表
		String hql = "from Factory where ctype='附件' and state=1";
		List<Factory> factoryList = factoryService.find(hql, Factory.class, null);
		super.put("factoryList", factoryList); //放入值栈中
		
		//5.跳页面
		return "toupdate";
	}
	
	/**
	 * update
	 */
	public String update() throws Exception {
		//调用业务    参数不能直接放model, 因为该页面只有3个值, 如果该部门对象之前有其他值, 那么就都丢掉了
		ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());  //根据id, 得到一个数据库中保存的部门对象
		
		//2.设置修改了的属性 (没改的属性沿用之前的)
		obj.setFactory(model.getFactory());
		obj.setFactoryName(model.getFactoryName());
		obj.setProductNo(model.getProductNo());
		obj.setProductImage(model.getProductImage());
		obj.setCnumber(model.getCnumber());
		obj.setAmount(model.getAmount());
		obj.setPackingUnit(model.getPackingUnit());
		obj.setPrice(model.getPrice());
		obj.setOrderNo(model.getOrderNo());
		obj.setProductDesc(model.getProductDesc());
		obj.setProductRequest(model.getProductRequest());
		
		extCproductService.saveOrUpdate(obj);
		return tocreate();
	}
	
	/**
	 * delete
	 * 
	 * 结论: 如果操作子项时, 最后同时传递他的所有祖宗
	 */
	public String delete() throws Exception {
		extCproductService.delete(ExtCproduct.class, model);
		
		return tocreate();
	}
	
}
