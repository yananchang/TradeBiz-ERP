package cn.ssh.tb.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.ssh.tb.dao.BaseDao;
import cn.ssh.tb.domain.ExportProduct;
import cn.ssh.tb.service.ExportProductService;
import cn.ssh.tb.utils.Page;
import cn.ssh.tb.utils.UtilFuns;

public class ExportProductServiceImpl implements ExportProductService {

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<ExportProduct> find(String hql, Class<ExportProduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public ExportProduct get(Class<ExportProduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public Page<ExportProduct> findPage(String hql, Page<ExportProduct> page, Class<ExportProduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public void saveOrUpdate(ExportProduct entity) {
		if(UtilFuns.isEmpty(entity.getId())) {
			//id没值,说明是新增
		}
		baseDao.saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<ExportProduct> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<ExportProduct> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);     //删除一个对象
	}

	public void delete(Class<ExportProduct> entityClass, Serializable[] ids) {
		
		for(Serializable id: ids) {
			this.deleteById(ExportProduct.class, id);
		}
	}


}
