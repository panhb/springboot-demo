package com.panhb.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panhb.demo.dao.UserRepository;
import com.panhb.demo.entity.User;
import com.panhb.demo.service.UserService;
import com.panhb.demo.service.base.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserRepository,User,Long> implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

}
