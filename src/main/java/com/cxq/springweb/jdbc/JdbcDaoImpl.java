package com.cxq.springweb.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.cxq.springweb.bean.User;

@Repository
public class JdbcDaoImpl implements JdbcDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public User getUserWithJdbc(String name) {
		String sql = "select * from User where name = ?";
//		return jdbcTemplate.query(sql, new Object[] { name }, new RowMapper<User>() {
//
//			@Override
//			public User mapRow(ResultSet rs, int arg1) throws SQLException {
//				User user = new User();
//				user.setName(rs.getString("name"));
//				user.setAge(rs.getString("age"));
//				user.setSex(rs.getString("sex"));
//				return user;
//			}
//		}).get(0);
		
		List<User> list = new ArrayList<User>();
		jdbcTemplate.query(sql, new Object[] { name }, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				User user = new User();
				user.setName(rs.getString("name"));
				user.setAge(rs.getString("age"));
				user.setSex(rs.getString("sex"));
				list.add(user);
			}
		});
		return list.get(0);
	}

}
