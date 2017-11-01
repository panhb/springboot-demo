package com.panhb.demo.controller;

import com.panhb.demo.model.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panhb.demo.controller.base.BaseController;
import com.panhb.demo.entity.User;
import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;
import com.panhb.demo.service.UserService;

import java.util.List;

/**
 * @author panhb
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public PageResult<User> index(){
		PageInfo pageInfo = this.initPageInfo(1, 10);
		return userService.pageBySql("select * from t_user", pageInfo, User.class);
	}

	@RequestMapping("/findAll")
	public List<User> findAll(){
		return userService.findAll();
	}

	@RequestMapping("/findById")
	public User findById(Long id){
		return userService.findById(id);
	}

	@RequestMapping("/exists")
	public Boolean exists(Long id){
		return userService.exists(id);
	}

	@RequestMapping("/count")
	public Long count(){
		return userService.count();
	}

	@RequestMapping("/delete")
	public Result delete(Long id){
		try{
			userService.delete(id);
			return Result.success();
		}catch (Exception e){
			log.error("删除失败",e);
		}
		return Result.error();
	}

}
