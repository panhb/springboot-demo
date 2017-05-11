package com.panhb.demo.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.panhb.demo.entity.User;
@Transactional
public interface UserDao extends JpaRepository<User,Long>{
	
	public User findByUserName(@Param(value = "userName") String userName);

}
