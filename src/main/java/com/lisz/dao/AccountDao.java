package com.lisz.dao;

import com.lisz.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(Account account) {
		String sql = "insert into account values(?, ?, ?, ?, ?, ?, ?, ?)";
		int rows = jdbcTemplate.update(sql, null, account.getUsername(), account.getPassword(), account.getNickName(),
							account.getAge(), account.getLocation(), account.getRole(), account.getProfileUrl());
		System.out.println(rows + " have been created");
	}
}
