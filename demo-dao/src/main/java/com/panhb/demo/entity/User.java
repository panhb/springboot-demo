package com.panhb.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.panhb.demo.model.base.BaseModel;
import lombok.Data;

@Entity
@Table(name = "t_user")
@Data
public class User extends BaseModel{

	private static final long serialVersionUID = 775638143722924325L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String userName;
	@JsonIgnore
	private String passWord;
	private Date createDate;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "t_user_role",
		joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName ="id")})
	private List<Role> roles;
	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getPassWord() {
//		return passWord;
//	}
//
//	public void setPassWord(String passWord) {
//		this.passWord = passWord;
//	}
//
//	public Date getCreateDate() {
//		return createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//
//	public List<Role> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(List<Role> roles) {
//		this.roles = roles;
//	}
//
//	@Override
//	public String toString() {
//		return MoreObjects.toStringHelper(this)
//				.add("id", id)
//				.add("userName", userName)
//				.add("passWord", passWord)
//				.add("createDate", createDate)
//				.add("roles", roles)
//				.toString();
//	}
	
	

}
