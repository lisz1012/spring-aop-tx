package com.lisz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate template;

	public void updateBalance(int userId, double balance) {
		String sql = "update account set balance = ? where id = ?";
		template.update(sql, balance, userId);
	}

	public double getPriceByBookId(int bookId) {
		String sql = "select price from book where id = ?";
		return template.queryForObject(sql, double.class, bookId);
	}

	public void updateStock (int bookId, int quantity) {
		String sql = "update book_stock set stock = stock - ? where id = ?";
		template.update(sql, quantity, bookId);
	}

	public double getBalanceByUserId(int userId) {
		String sql = "select balance from account where id = ?";
		return template.queryForObject(sql, double.class, userId);
	}

	public int getInstockQuantity(int bookId) {
		String sql = "select stock from book_stock where id = ?";
		return template.queryForObject(sql, int.class, bookId);
	}

	public int updatePrice(int bookId, double price) {
		String sql = "update book set price = ? where id = ?";
		return template.update(sql, price, bookId);
	}
}
