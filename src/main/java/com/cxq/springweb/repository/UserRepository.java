package com.cxq.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cxq.springweb.bean.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>{
	

	@Modifying
	@Query(value = " delete from User where id in ?1")
	public int deleteUsers(String... ids);
}
