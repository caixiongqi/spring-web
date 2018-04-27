package com.cxq.springweb.jdbc;

import com.cxq.springweb.bean.User;

public interface JdbcDao {
	
	User getUserWithJdbc(String name);

}
