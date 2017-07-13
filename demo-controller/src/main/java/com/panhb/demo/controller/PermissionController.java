package com.panhb.demo.controller;

import com.panhb.demo.controller.base.BaseController;
import com.panhb.demo.entity.Permission;
import com.panhb.demo.model.result.Result;
import com.panhb.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController{
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/save")
	public Result save(Permission permission){
		permissionService.save(permission);
		return Result.success();
	}

	@RequestMapping("/findAll")
	public List<Permission> findAll(){
		return permissionService.findAll();
	}

}
