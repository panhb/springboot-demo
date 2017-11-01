package com.panhb.demo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.google.common.base.MoreObjects;
import com.panhb.demo.model.base.BaseModel;
import lombok.Data;

/**
 * @author panhb
 */
@Entity
@Table(name = "t_role_permission")
@Data
public class RolePermission extends BaseModel{

	private static final long serialVersionUID = 775638143722924325L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long roleId;
	private Long permissionId;
	private String status;
	private Date createDate;

}
