package com.cxq.springweb.service;

import java.util.List;

import com.cxq.springweb.bean.JsonResult;
import com.cxq.springweb.bean.PageQueryParam;
import com.cxq.springweb.bean.User;

public interface UserService {
	
	JsonResult getUserListPage(PageQueryParam pageQueryParam, User user);
	
	User createUser(User user);
	
	List<User> getUserList();
	
	void deleteUser(String ids);
	
	User getUserById(String id);

 }
