package com.panhb.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panhb.demo.dao.UserDao;
import com.panhb.demo.entity.User;
import com.panhb.demo.service.UserService;
import com.panhb.demo.service.base.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	public User findById(Long id) {
		return userDao.findOne(id);
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public User save(User user) {
		return userDao.save(user);
	}

	public boolean exists(Long id) {
		return userDao.exists(id);
	}

	public long count() {
		return userDao.count();
	}

	public void delete(Long id) {
		userDao.delete(id);
	}

	public void delete(User entity) {
		userDao.delete(entity);
	}

	public void delete(List<User> entities) {
		userDao.delete(entities);
	}

	public void deleteAll() {
		userDao.deleteAll();
	}
	
}
