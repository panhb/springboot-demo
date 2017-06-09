package com.panhb.demo.dao;

import com.panhb.demo.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "permission")
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>{
	

}
