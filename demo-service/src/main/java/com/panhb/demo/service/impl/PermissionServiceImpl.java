package com.panhb.demo.service.impl;

import com.panhb.demo.dao.PermissionRepository;
import com.panhb.demo.dao.UserRepository;
import com.panhb.demo.entity.Permission;
import com.panhb.demo.entity.User;
import com.panhb.demo.service.FilterChainDefinitionsService;
import com.panhb.demo.service.PermissionService;
import com.panhb.demo.service.UserService;
import com.panhb.demo.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission,Long> implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private FilterChainDefinitionsService filterChainDefinitionsService;
	
	public Permission findById(Long id) {
		return permissionRepository.findOne(id);
	}

	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	public void save(Permission entity) {
		permissionRepository.save(entity);
		filterChainDefinitionsService.reloadFilterChains();
	}

	public boolean exists(Long id) {
		return permissionRepository.exists(id);
	}

	public long count() {
		return permissionRepository.count();
	}

	public void delete(Long id) {
		permissionRepository.delete(id);
		filterChainDefinitionsService.reloadFilterChains();
	}

	public void delete(Permission entity) {
		permissionRepository.delete(entity);
		filterChainDefinitionsService.reloadFilterChains();
	}

	public void delete(List<Permission> entities) {
		permissionRepository.delete(entities);
		filterChainDefinitionsService.reloadFilterChains();
	}

	public void deleteAll() {
		permissionRepository.deleteAll();
		filterChainDefinitionsService.reloadFilterChains();
	}


}
