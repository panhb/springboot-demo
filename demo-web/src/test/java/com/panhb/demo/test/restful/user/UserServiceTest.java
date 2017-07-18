package com.panhb.demo.test.restful.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.panhb.demo.service.UserService;
import com.panhb.demo.test.restful.BaseTest;

public class UserServiceTest extends BaseTest {
	
	@Autowired
	UserService userService;
	
	@Test
	public void testUserQuery() {
		System.out.println(userService.findByUsername("2"));
	}
}
