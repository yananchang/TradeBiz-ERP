package cn.ssh.tb.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Dept implements Serializable {

	private String id;
	private Set<User> users = new HashSet<User>(0);   //dept & users: one to many
	private String deptName;   //department name
	private Dept parent;        //parent dept, self-related   sub-dept & parent-dept: many to one
	private Integer state;     //状态  1:activated   0:deactivated
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Dept getParent() {
		return parent;
	}
	public void setParent(Dept parent) {
		this.parent = parent;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
}
