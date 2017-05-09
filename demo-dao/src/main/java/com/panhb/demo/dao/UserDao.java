package com.panhb.demo.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panhb.demo.entity.User;
@Transactional
public interface UserDao extends JpaRepository<User,Long>{

}
