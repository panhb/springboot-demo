package com.panhb.demo.controller;

import com.panhb.demo.controller.base.BaseController;
import com.panhb.demo.entity.User;
import com.panhb.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author panhb
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController extends BaseController{

	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public String index(){
		return "redirect:/test/findAll";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public List<User> findAll(){
		return userService.findAll();
	}



}
