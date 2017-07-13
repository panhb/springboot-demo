package com.panhb.demo.service;

import java.util.List;

import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;
import org.springframework.transaction.annotation.Transactional;

import com.panhb.demo.entity.User;
import com.panhb.demo.service.base.BaseService;

@Transactional
public interface UserService extends BaseService<User,Long>{
	
	public User findById(Long id);
	
	public List<User> findAll();
	
	public User save(User entity);
	
	public boolean exists(Long id);
	
	public long count();
	
	public void delete(Long id);
	
	public void delete(User entity);
	
	public void delete(List<User> entities);
	
	public void deleteAll();
	
	public User findByUserName(String userName);

	public PageResult<User> pageBySql(String sql, PageInfo pageInfo, Class<User> mappedClass);

}
