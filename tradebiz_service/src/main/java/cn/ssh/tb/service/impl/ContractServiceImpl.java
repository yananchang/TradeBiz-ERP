package cn.ssh.tb.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.ssh.tb.dao.BaseDao;
import cn.ssh.tb.domain.Contract;
import cn.ssh.tb.service.ContractService;
import cn.ssh.tb.utils.Page;
import cn.ssh.tb.utils.UtilFuns;

public class ContractServiceImpl implements ContractService {

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public Contract get(Class<Contract> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public Page<Contract> findPage(String hql, Page<Contract> page, Class<Contract> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public void saveOrUpdate(Contract entity) {
		if(UtilFuns.isEmpty(entity.getId())) {
			//id没值,说明是新增
			entity.setTotalAmount(0d);
			entity.setState(0);   //0:草稿   1已上报  2 已报运
		}
		baseDao.saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<Contract> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<Contract> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);     //删除一个对象
	}

	public void delete(Class<Contract> entityClass, Serializable[] ids) {
		
		for(Serializable id: ids) {
			this.deleteById(Contract.class, id);
		}
	}

	public void changeState(String[] ids, Integer state) {
		for(String id: ids) {
			Contract contract = baseDao.get(Contract.class, id);
			contract.setState(state);
			baseDao.saveOrUpdate(contract); //该行可以不写, 因为持久态对象;
		}
	}

}
