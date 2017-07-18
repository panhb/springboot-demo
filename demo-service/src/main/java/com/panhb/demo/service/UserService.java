package com.panhb.demo.service;

import com.panhb.demo.dao.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import com.panhb.demo.entity.User;
import com.panhb.demo.service.base.BaseService;

@Transactional
public interface UserService extends BaseService<UserRepository,User,Long>{
	

	public User findByUsername(String username);

}
