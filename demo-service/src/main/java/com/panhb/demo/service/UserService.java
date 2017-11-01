package com.panhb.demo.service;

import com.panhb.demo.dao.UserRepository;

import com.panhb.demo.entity.User;
import com.panhb.demo.service.base.BaseService;

/**
 * @author panhb
 */
public interface UserService extends BaseService<UserRepository,User,Long>{

	/**
	 * findByUsername
	 *
	 * @param username
	 * @return User
	 */
	User findByUsername(String username);

}
