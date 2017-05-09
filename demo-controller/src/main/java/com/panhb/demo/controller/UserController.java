package com.panhb.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panhb.demo.controller.base.BaseController;
import com.panhb.demo.entity.User;
import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;
import com.panhb.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public PageResult<User> index(){
		PageInfo pageInfo = this.initPageInfo(1, 10);
		return userService.pageBySql("select * from t_user", pageInfo, User.class);
	}

}
