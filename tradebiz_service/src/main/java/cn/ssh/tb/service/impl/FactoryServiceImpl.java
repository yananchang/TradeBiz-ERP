package cn.ssh.tb.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.ssh.tb.dao.BaseDao;
import cn.ssh.tb.domain.Factory;
import cn.ssh.tb.service.FactoryService;
import cn.ssh.tb.utils.Page;
import cn.ssh.tb.utils.UtilFuns;

public class FactoryServiceImpl implements FactoryService {

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public Factory get(Class<Factory> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public Page<Factory> findPage(String hql, Page<Factory> page, Class<Factory> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public void saveOrUpdate(Factory entity) {
		if(UtilFuns.isEmpty(entity.getId())) {
			//id没值,说明是新增
		}
		baseDao.saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<Factory> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<Factory> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);     //删除一个对象
	}

	public void delete(Class<Factory> entityClass, Serializable[] ids) {
		
		for(Serializable id: ids) {
			this.deleteById(Factory.class, id);
		}
	}

}
