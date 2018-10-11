package cn.ssh.tb.domain;

import java.util.HashSet;
import java.util.Set;

public class User extends BaseEntity {
	private String id;
	private Dept dept;           // user to dept: many-to-one
	private Userinfo userinfo;   // user to userinfo: one-to-one 
	private String userName;     // username
	private String password;     //password   to be encrypted with MD5
	private Integer state;       //state

	private Set<Role> roles = new HashSet<Role>(0);   //user to roles: many-to-many
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Userinfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
