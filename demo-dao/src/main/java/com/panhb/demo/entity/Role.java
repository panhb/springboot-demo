package com.panhb.demo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.panhb.demo.model.base.BaseModel;
import lombok.Data;

@Entity
@Table(name = "t_role")
@Data
public class Role extends BaseModel{

	private static final long serialVersionUID = 775638143722924325L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String roleName;
	private String status;
	private Date createDate;
	private Date updateDate;
	
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "t_role_permission",
//		joinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id")},
//			inverseJoinColumns = {@JoinColumn(name = "permissionId", referencedColumnName ="id")})
//	private List<Permission> permissions;

}
