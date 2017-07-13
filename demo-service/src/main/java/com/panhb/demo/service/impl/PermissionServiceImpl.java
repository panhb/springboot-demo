package com.panhb.demo.service.impl;

import com.panhb.demo.dao.PermissionRepository;
import com.panhb.demo.entity.Permission;
import com.panhb.demo.service.FilterChainDefinitionsService;
import com.panhb.demo.service.PermissionService;
import com.panhb.demo.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionRepository,Permission,Long> implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private FilterChainDefinitionsService filterChainDefinitionsService;
	
	public void saveAndRefresh(Permission entity) {
		permissionRepository.save(entity);
		filterChainDefinitionsService.reloadFilterChains();
	}

	public void deleteAndRefresh(Long id) {
		permissionRepository.delete(id);
		filterChainDefinitionsService.reloadFilterChains();
	}

	public void deleteAndRefresh(Permission entity) {
		permissionRepository.delete(entity);
		filterChainDefinitionsService.reloadFilterChains();
	}

	public void deleteAndRefresh(List<Permission> entities) {
		permissionRepository.delete(entities);
		filterChainDefinitionsService.reloadFilterChains();
	}

	public void deleteAllAndRefresh() {
		permissionRepository.deleteAll();
		filterChainDefinitionsService.reloadFilterChains();
	}


}
