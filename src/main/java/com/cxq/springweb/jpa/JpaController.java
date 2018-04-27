package com.cxq.springweb.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxq.springweb.bean.User;
import com.cxq.springweb.jdbc.JdbcDao;

/**
 * 测试JPA与JDBC方法用
 */
@Controller
@RequestMapping(value = "jpa")
public class JpaController {
	
	@Autowired
	private JpaTypeRepository jpaTypeRepository;
	
	@Autowired
	private JdbcDao jdbcDao;
	
	
	@RequestMapping(value = "/testPage")
	public String toTestPage() {
		return "jpa/jpa";
	}
	
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public User getUser(@RequestParam(value = "name") String name) {
		//return jpaTypeRepository.getUser(name);
		//return jpaTypeRepository.findByName(name);
		return jdbcDao.getUserWithJdbc(name);
	}
}
