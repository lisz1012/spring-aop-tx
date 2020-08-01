package com.lisz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MultiService {
	@Autowired
	private BookService bookService;

	@Transactional
	public void mult() {
		try {
			bookService.buyBook(1, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//bookService.buyBook(1, 1, 1);
		System.out.println("--------");
		bookService.updatePrice(1, 3);
		//int i = 1 / 0;
	}

	//@Transactional
	public void buyBook() {
		bookService.buyBook(1, 1, 1);
	}
}
