package com.panhb.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.panhb.demo.entity.User;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "user")
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findByUserName(@Param(value = "userName") String userName);

}
