package com.cxq.springweb.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cxq.springweb.bean.User;

public interface JpaTypeRepository extends JpaRepository<User, String>{
	
	User findByName(String name);
	
	@Query(value = "from User where name = ?1")
	public User getUser(String name);

}
