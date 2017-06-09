package com.panhb.demo.service;

import com.panhb.demo.entity.Permission;
import com.panhb.demo.entity.User;
import com.panhb.demo.service.base.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PermissionService extends BaseService<Permission,Long>{
	
	public Permission findById(Long id);
	
	public List<Permission> findAll();
	
	public void save(Permission entity);
	
	public boolean exists(Long id);
	
	public long count();
	
	public void delete(Long id);
	
	public void delete(Permission entity);
	
	public void delete(List<Permission> entities);
	
	public void deleteAll();
	

}
