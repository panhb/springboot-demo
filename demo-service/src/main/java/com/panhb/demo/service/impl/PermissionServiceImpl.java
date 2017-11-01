package com.panhb.demo.service.impl;

import com.panhb.demo.dao.PermissionRepository;
import com.panhb.demo.entity.Permission;
import com.panhb.demo.service.FilterChainDefinitionsService;
import com.panhb.demo.service.PermissionService;
import com.panhb.demo.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author panhb
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends BaseServiceImpl<PermissionRepository,Permission,Long> implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private FilterChainDefinitionsService filterChainDefinitionsService;

	@Override
	public void saveAndRefresh(Permission entity) {
		permissionRepository.save(entity);
		filterChainDefinitionsService.reloadFilterChains();
	}

	@Override
	public void deleteAndRefresh(Long id) {
		permissionRepository.delete(id);
		filterChainDefinitionsService.reloadFilterChains();
	}

	@Override
	public void deleteAndRefresh(Permission entity) {
		permissionRepository.delete(entity);
		filterChainDefinitionsService.reloadFilterChains();
	}

	@Override
	public void deleteAndRefresh(List<Permission> entities) {
		permissionRepository.delete(entities);
		filterChainDefinitionsService.reloadFilterChains();
	}

	@Override
	public void deleteAllAndRefresh() {
		permissionRepository.deleteAll();
		filterChainDefinitionsService.reloadFilterChains();
	}


}
