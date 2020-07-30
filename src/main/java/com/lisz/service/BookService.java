package com.lisz.service;

import com.lisz.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao;

	@Transactional
	public void buyBook(int userId, int bookId, int quantity) {
		double bookPrice = bookDao.getPriceByBookId(bookId);
		double balance = bookDao.getBalanceByUserId(userId);
		if (balance <= bookPrice * quantity) throw new IllegalArgumentException("Balance low");
		int instockQuantity = bookDao.getInstockQuantity(bookId);
		if (instockQuantity < quantity) throw new IllegalArgumentException(("Not enough instock quantity"));
		balance -= bookPrice * quantity;
		bookDao.updateBalance(userId, balance);
		// 在两个写操作之间抛出异常
		int i = 1/0;
		bookDao.updateStock(bookId, quantity);
		System.out.println(String.format("User %s just bought %s books with book ID: %s",
				userId, quantity, bookId));
	}


	public void updateBalance(int userId, double balance) {
		bookDao.updateBalance(userId, balance);
	}

	public double getPriceByBookId(int bookId) {
		return bookDao.getPriceByBookId(bookId);
	}

	public void updateStock (int bookId, int quantity) {
		bookDao.updateStock(bookId, quantity);
	}
}
