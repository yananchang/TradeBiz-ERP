package cn.ssh.tb.action.cargo;

import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ModelDriven;

import cn.ssh.tb.action.BaseAction;
import cn.ssh.tb.domain.Contract;
import cn.ssh.tb.domain.Export;
import cn.ssh.tb.domain.ExportProduct;
import cn.ssh.tb.domain.User;
import cn.ssh.tb.service.ContractService;
import cn.ssh.tb.service.ExportProductService;
import cn.ssh.tb.service.ExportService;
import cn.ssh.tb.utils.Page;
import cn.ssh.tb.utils.UtilFuns;

/**
 * @author Yanan Chang
 *
 */
public class ExportAction extends BaseAction implements ModelDriven<Export> {
	//模型驱动
	private Export model = new Export();
	public Export getModel() {
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
	
	//注入ExportService
	private ExportService exportService;
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	private ContractService contractService;
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	private ExportProductService exportProductService;
	public void setExportProductService(ExportProductService exportProductService) {
		this.exportProductService = exportProductService;
	}
	/**
	 * 分页查询  将数据封装到一个page分页工具类对象.   注意, page中还有分页条links(首页 上一页  下一页 末页...)
	 */
	public String list() throws Exception {
		String hql = "from Export where 1=1 ";
		
		/*//如何确定用户的等级
		User user = super.getCurUser();
		int degree = user.getUserinfo().getDegree();
		if(degree==4) {
			//说明是员工
			hql+=" and createBy='" + user.getId() +"'";
		}else if(degree==3) {
			//说明是部门经理, 管理本部门
			hql+=" and createDept='" + user.getDept().getId() +"'";
		}else if(degree==2) {
			//说明是管理本部门及下属部门
			
		}else if(degree==1) {
			//说明是副总
			
		}else if(degree==0) {
			//说明是总经理
			
		}*/
		
		page = exportService.findPage(hql, page, Export.class, null);
		
		//设置分页的url地址
		page.setUrl("exportAction_list");  //re-enter this method to re-query the page
		
		//将page对象压入到值栈的栈顶, 这样, 从值栈取出page对象时比较方便, 以后user什么的也会压栈顶, 所以可以抽取成方法
		super.push(page);
		
		
		return "list";
	}
	
	
	
	
	/**
	 * 查询状态为1的购销合同
	 */
	public String contractList() throws Exception {
		//查询状态为1的购销合同
		String hql = "from Contract where state=1";
		//分页查询
		contractService.findPage(hql,page, Contract.class, null);
		
		page.setUrl("exportAction_contractList");
		
		//放入值栈
		super.push(page);
		
		return "contractList";
	}
	
	/**
	 * 查看
	 *    id=sdfsdf
	 *    model对象   在栈顶, 本身有id属性
	 */
	public String toview() throws Exception {
		//1.调用业务方法, 根据id,得到对象
		Export dept = exportService.get(Export.class, model.getId());
		
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
		
		//跳页面
		return "tocreate";
	}
	/**
	 * 保存  要修改
	 *<input type="hidden" name="contractIds" value="4028817a33812ffd0133813f25940001, 4028817a33812ffd013382048ff80024, 4028817a33812ffd0133821a8eb5002b" />
	 * model对象是Export类型
	 *     id:
	 *     contractIds:"4028817a33812ffd0133813f25940001, 4028817a33812ffd013382048ff80024, 4028817a33812ffd0133821a8eb5002b"
	 */
	public String insert() throws Exception {
		//1.加入细粒度权限控制的数据
		User user = super.getCurUser();  //在BaseAction中有session属性,实现了SessionAware接口;
		model.setCreateBy(user.getId()); //设置创建者的id
		model.setCreateDept(user.getDept().getId());  //设置创建者所在部门的id
		
		//1.调用业务方法,直线保存
		exportService.saveOrUpdate(model); 
		
		//跳页面
		return contractList();
	}
	
	/**
	 * enter modify page
	 */
	public String toupdate() throws Exception {
		//1.根据id, 得到一个对象
		Export obj = exportService.get(Export.class, model.getId());
		
		//2.将对象放入值栈中
		super.push(obj);
		
		//3.addTRRecord("mRecordTable", id, productNo, cnumber, grossWeight, netWeight, sizeLength, sizeWidth, sizeHeight, exPrice, tax);
		StringBuilder sb = new StringBuilder();
		Set<ExportProduct> epSet = obj.getExportProducts();  //关联级别的数据检索
//		遍历集合
		for(ExportProduct ep: epSet) {
			sb.append("addTRRecord(\"mRecordTable\", \"").append(ep.getId());
			sb.append("\", \"").append(ep.getProductNo());
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getCnumber()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getGrossWeight()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getNetWeight()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getSizeLength()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getSizeWidth()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getSizeHeight()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getExPrice()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getTax())).append("\");");
		}
		
		//4.将拼好的串放入值栈中
		super.put("mRecordData", sb.toString());
		
		//5.跳页面
		return "toupdate";
	}
	
	/**
	 * update
	 */
	public String update() throws Exception {
		//调用业务    参数不能直接放model, 因为该页面只有3个值, 如果该部门对象之前有其他值, 那么就都丢掉了
		Export obj = exportService.get(Export.class, model.getId());  //根据id, 得到一个数据库中保存的部门对象
		
		//2.设置修改了的属性 (没改的属性沿用之前的)
		obj.setInputDate(model.getInputDate());
		obj.setLcno(model.getLcno());
		obj.setConsignee(model.getConsignee());
		obj.setShipmentPort(model.getShipmentPort());
		obj.setDestinationPort(model.getDestinationPort());
		obj.setTransportMode(model.getTransportMode());
		obj.setPriceCondition(model.getPriceCondition());
		obj.setMarks(model.getMarks());
		obj.setRemark(model.getRemark());
		
		Set<ExportProduct> epSet = new HashSet<>();  //商品列表
		
		for(int i=0;i<mr_id.length;i++) {
			//遍历数组, 得到每个商品对象
			ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i]);
			epSet.add(ep);
			
			if("1".equals(mr_changed[i])) {
				ep.setCnumber(mr_cnumber[i]);
				ep.setGrossWeight(mr_grossWeight[i]);
				ep.setNetWeight(mr_netWeight[i]);
				ep.setSizeLength(mr_sizeLength[i]);
				ep.setSizeWidth(mr_sizeWidth[i]);
				ep.setSizeHeight(mr_sizeHeight[i]);
				ep.setExPrice(mr_exPrice[i]);
				ep.setTax(mr_tax[i]);
			}
		}
		
		//设置报运单与商品列表的关系
		obj.setExportProducts(epSet);
		
		
		exportService.saveOrUpdate(obj);
		return "alist";
	}
	private String mr_changed[];
	private String mr_id[];
	private Integer mr_cnumber[];
	private Double mr_grossWeight[];
	private Double mr_netWeight[];
	private Double mr_sizeLength[];
	private Double mr_sizeWidth[];
	private Double mr_sizeHeight[];
	private Double mr_exPrice[];
	private Double mr_tax[];
	public void setMr_changed(String[] mr_changed) {
		this.mr_changed = mr_changed;
	}
	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}
	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}
	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}
	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}
	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}
	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}
	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}
	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}
	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}
	/**
	 * delete
	 */
	public String delete() throws Exception {
		String ids[] = model.getId().split(", ");
		
		//调用业务方法, 实现批量删除
		exportService.delete(Export.class, ids);
		
		return "alist";
	}
	
	/**
	 * submit
	 */
	public String submit() throws Exception {
		String ids[] = model.getId().split(", ");
		
		//2. 遍历ids, 并加载出每个购销合同对象, 再修改购销合同的状态
		exportService.changeState(ids, 1);
		
		return "alist";
	}
	
	/**
	 * cancel
	 */
	public String cancel() throws Exception {
		String ids[] = model.getId().split(", ");
		
		//2. 遍历ids, 并加载出每个购销合同对象, 再修改购销合同的状态
		exportService.changeState(ids, 0);
		
		return "alist";
	}
}
