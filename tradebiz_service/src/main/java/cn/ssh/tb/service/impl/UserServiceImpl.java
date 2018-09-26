package cn.ssh.tb.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import cn.ssh.tb.dao.BaseDao;
import cn.ssh.tb.domain.User;
import cn.ssh.tb.service.UserService;
import cn.ssh.tb.utils.Encrypt;
import cn.ssh.tb.utils.MailUtil;
import cn.ssh.tb.utils.Page;
import cn.ssh.tb.utils.SysConstant;
import cn.ssh.tb.utils.UtilFuns;

public class UserServiceImpl implements UserService {

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	private SimpleMailMessage mailMessage;
	private JavaMailSender mailSender;

	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public List<User> find(String hql, Class<User> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public User get(Class<User> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public void saveOrUpdate(final User entity) {
		if(UtilFuns.isEmpty(entity.getId())) {     //check if id is null(if it is a new user)
			//id没值,说明是新增
			String id = UUID.randomUUID().toString();
			entity.setId(id);      //基于主键的一对一, 说明两个表的id都是一个            user table & user info table, one-to-one, use same primary key
			entity.getUserinfo().setId(id);
			
			//补Shiro添加后的bug
			entity.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS,entity.getUserName()));
			
			baseDao.saveOrUpdate(entity);  //记录保存
			
			//再开启一个新的线程, 完成邮件发送功能
			/*Thread th = new Thread(new Runnable(){
				public void run() {
					try {
						MailUtil.sendMessage(entity.getUserinfo().getEmail(), "use employee Account notice", "Welcome join the company, ur username:"+ entity.getUserName()+", initial password: "+ SysConstant.DEFAULT_PASS );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			th.start();*/
			
			//spring集成javamail
			Thread th = new Thread(new Runnable(){
				public void run() {
					try {
						mailMessage.setTo(entity.getUserinfo().getEmail());
						mailMessage.setSubject("use employee Account notice");
						mailMessage.setText("Welcome join the company, ur username:"+ entity.getUserName()+", initial password: "+ SysConstant.DEFAULT_PASS);
						
						mailSender.send(mailMessage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			th.start();
			
		}else {
			//修改
			baseDao.saveOrUpdate(entity);
		}
	}

	public void saveOrUpdateAll(Collection<User> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<User> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);     //删除一个对象
	}

	public void delete(Class<User> entityClass, Serializable[] ids) {
		
		for(Serializable id: ids) {
			this.deleteById(User.class, id);
		}
	}

}
