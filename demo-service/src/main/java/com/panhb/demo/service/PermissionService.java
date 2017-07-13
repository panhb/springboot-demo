package com.panhb.demo.service;

import com.panhb.demo.dao.PermissionRepository;
import com.panhb.demo.entity.Permission;
import com.panhb.demo.entity.User;
import com.panhb.demo.service.base.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PermissionService extends BaseService<PermissionRepository,Permission,Long>{

	public void saveAndRefresh(Permission entity);

	public void deleteAndRefresh(Long id);

	public void deleteAndRefresh(Permission entity);

	public void deleteAndRefresh(List<Permission> entities);

	public void deleteAllAndRefresh();
	
}
