package com.panhb.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panhb.demo.dao.UserRepository;
import com.panhb.demo.entity.User;
import com.panhb.demo.service.UserService;
import com.panhb.demo.service.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author panhb
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserRepository,User,Long> implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
