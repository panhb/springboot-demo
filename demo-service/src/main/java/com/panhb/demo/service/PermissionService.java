package com.panhb.demo.service;

import com.panhb.demo.dao.PermissionRepository;
import com.panhb.demo.entity.Permission;
import com.panhb.demo.service.base.BaseService;

import java.util.List;

/**
 * @author panhb
 */
public interface PermissionService extends BaseService<PermissionRepository,Permission,Long>{

	/**
	 * saveAndRefresh
	 *
	 * @param entity
	 */
	void saveAndRefresh(Permission entity);

	/**
	 * deleteAndRefresh
	 *
	 * @param id
	 */
	void deleteAndRefresh(Long id);

	/**
	 * deleteAndRefresh
	 *
	 * @param entity
	 */
	void deleteAndRefresh(Permission entity);

	/**
	 * deleteAndRefresh
	 *
	 * @param entities
	 */
	void deleteAndRefresh(List<Permission> entities);

	/**
	 * deleteAllAndRefresh
	 */
	void deleteAllAndRefresh();
	
}
