package com.cxq.springweb.service;

import java.util.List;

import com.cxq.springweb.bean.JsonResult;
import com.cxq.springweb.bean.PageQueryParam;
import com.cxq.springweb.bean.User;

public interface UserService {
	
	/**
	 * 分页加条件查询
	 * @param pageQueryParam
	 * @param user 用户实体
	 * @return bootstrap table所需要格式的结果集
	 */
	JsonResult getUserListPage(PageQueryParam pageQueryParam, User user);
	
	User createUser(User user);
	
	List<User> getUserList();
	
	void deleteUser(String ids);
	
	User getUserById(String id);

 }
