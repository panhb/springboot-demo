package com.panhb.demo.service.impl;

import java.util.Date;
import java.util.List;

import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panhb.demo.dao.UserRepository;
import com.panhb.demo.entity.User;
import com.panhb.demo.service.UserService;
import com.panhb.demo.service.base.BaseServiceImpl;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User save(User entity) {
		return userRepository.save(entity);
	}

	public boolean exists(Long id) {
		return userRepository.exists(id);
	}

	public long count() {
		return userRepository.count();
	}

	public void delete(Long id) {
		userRepository.delete(id);
	}

	public void delete(User entity) {
		userRepository.delete(entity);
	}

	public void delete(List<User> entities) {
		userRepository.delete(entities);
	}

	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public PageResult<User> pageBySql(String sql, PageInfo pageInfo, Class<User> mappedClass) {
		return userRepository.pageBySql(sql,pageInfo,mappedClass);
	}

}
