package com.lisz.service;

import com.lisz.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao;

	/**
	 * propagation: 传播特性：表示不同的事务之间执行的关系，事务套着事务
	 * isolation：隔离级别，4种隔离级别，会引发不同的数据错乱问题
	 * timeout：超时时间 timeout = 3 就是3秒钟之后事务会报异常org.springframework.transaction.TransactionTimedOutException
	 *          然后正常回滚
	 * readonly：只读事务:如果配置了只读事务，那么在事务运行期间，不允许对数据进行修改，否则抛出异常:Caused by:
	 *          java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed
	 *          在做数据查询的时候，加上这个标识就是所有读操作执行完毕之前不允许有其他的写操作对被读取的数据进行修改。所以几个操作里面
	 *          既有读又有写，则不要设置这一项。设置哪些异常不会回滚数据
	 * noRollBackfor: noRollbackFor = {ArithmeticException.class}
	 * noRollbackForClassName: 接受一个String， 跟上面是一个功能的两种不同的方式而已
	 * 设置哪些异常回滚
	 * rollBackfor: RuntimeException和Error会默认回滚，但是其他的不回滚，这里强制指定一下需要回滚的其他的Throwables
	 * rollbackForClassName：
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void buyBook(int userId, int bookId, int quantity) {
		double bookPrice = bookDao.getPriceByBookId(bookId);
		double balance = bookDao.getBalanceByUserId(userId);
		if (balance <= bookPrice * quantity) throw new IllegalArgumentException("Balance low");
		int instockQuantity = bookDao.getInstockQuantity(bookId);
		if (instockQuantity < quantity) throw new IllegalArgumentException(("Not enough instock quantity"));
		balance -= bookPrice * quantity;
		bookDao.updateBalance(userId, balance);
		// 在两个写操作之间抛出异常, 如果加上try catch，在catch里面不throw 出异常，则不会触发事务回滚，顺利完成
		// 如果这个try全部包住，则只会更新balance不会更新库存
		//int i = 1/0;
		bookDao.updateStock(bookId, quantity);
		System.out.println(String.format("User %s just bought %s books with book ID: %s",
				userId, quantity, bookId));
//		try {
//			int i = 1/0;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	// 测试timeout
	@Transactional(timeout = 3)
	public void buyBook02(int userId, int bookId, int quantity) {
		double bookPrice = bookDao.getPriceByBookId(bookId);
		double balance = bookDao.getBalanceByUserId(userId);
		if (balance <= bookPrice * quantity) throw new IllegalArgumentException("Balance low");
		int instockQuantity = bookDao.getInstockQuantity(bookId);
		if (instockQuantity < quantity) throw new IllegalArgumentException(("Not enough instock quantity"));
		balance -= bookPrice * quantity;
		bookDao.updateBalance(userId, balance);
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bookDao.updateStock(bookId, quantity);
		System.out.println(String.format("User %s just bought %s books with book ID: %s",
				userId, quantity, bookId));
	}

	// 测试readonly
	@Transactional(readOnly = true)
	public void buyBook03(int userId, int bookId, int quantity) {
		double bookPrice = bookDao.getPriceByBookId(bookId);
		double balance = bookDao.getBalanceByUserId(userId);
		if (balance <= bookPrice * quantity) throw new IllegalArgumentException("Balance low");
		int instockQuantity = bookDao.getInstockQuantity(bookId);
		if (instockQuantity < quantity) throw new IllegalArgumentException(("Not enough instock quantity"));
		balance -= bookPrice * quantity;
		bookDao.updateBalance(userId, balance);
		bookDao.updateStock(bookId, quantity);
		System.out.println(String.format("User %s just bought %s books with book ID: %s",
				userId, quantity, bookId));
	}

	// 测试 noRollbackFor
	@Transactional(noRollbackFor = ArithmeticException.class) // 余额改变库存不变。int i = 1 / 0; 在最后的话会保留所有修改
	public void buyBook04(int userId, int bookId, int quantity) {
		double bookPrice = bookDao.getPriceByBookId(bookId);
		double balance = bookDao.getBalanceByUserId(userId);
		if (balance <= bookPrice * quantity) throw new IllegalArgumentException("Balance low");
		int instockQuantity = bookDao.getInstockQuantity(bookId);
		if (instockQuantity < quantity) throw new IllegalArgumentException(("Not enough instock quantity"));
		balance -= bookPrice * quantity;
		bookDao.updateBalance(userId, balance);
		int i = 1 / 0;
		bookDao.updateStock(bookId, quantity);
		System.out.println(String.format("User %s just bought %s books with book ID: %s",
				userId, quantity, bookId));
	}

	// 测试 noRollbackForClassName
	@Transactional(noRollbackForClassName = "java.lang.ArithmeticException") // 余额改变库存不变，int i = 1 / 0; 在最后的话会保留所有修改
	public void buyBook05(int userId, int bookId, int quantity) {
		double bookPrice = bookDao.getPriceByBookId(bookId);
		double balance = bookDao.getBalanceByUserId(userId);
		if (balance <= bookPrice * quantity) throw new IllegalArgumentException("Balance low");
		int instockQuantity = bookDao.getInstockQuantity(bookId);
		if (instockQuantity < quantity) throw new IllegalArgumentException(("Not enough instock quantity"));
		balance -= bookPrice * quantity;
		bookDao.updateBalance(userId, balance);
		int i = 1 / 0;
		bookDao.updateStock(bookId, quantity);
		System.out.println(String.format("User %s just bought %s books with book ID: %s",
				userId, quantity, bookId));
	}

	// 测试 noRollbackFor
	@Transactional(rollbackFor = FileNotFoundException.class) // 余额库存都不变。
	public void buyBook06(int userId, int bookId, int quantity) throws FileNotFoundException {
		double bookPrice = bookDao.getPriceByBookId(bookId);
		double balance = bookDao.getBalanceByUserId(userId);
		if (balance <= bookPrice * quantity) throw new IllegalArgumentException("Balance low");
		int instockQuantity = bookDao.getInstockQuantity(bookId);
		if (instockQuantity < quantity) throw new IllegalArgumentException(("Not enough instock quantity"));
		balance -= bookPrice * quantity;
		bookDao.updateBalance(userId, balance);
		bookDao.updateStock(bookId, quantity);
		System.out.println(String.format("User %s just bought %s books with book ID: %s",
				userId, quantity, bookId));
		int i = 1 / 0;
		new FileInputStream("aaa.txt");
	}

	// 测试 noRollbackForClassName
	@Transactional(rollbackForClassName = "java.io.FileNotFoundException") // 余额库存都不变
	public void buyBook07(int userId, int bookId, int quantity) throws FileNotFoundException {
		double bookPrice = bookDao.getPriceByBookId(bookId);
		double balance = bookDao.getBalanceByUserId(userId);
		if (balance <= bookPrice * quantity) throw new IllegalArgumentException("Balance low");
		int instockQuantity = bookDao.getInstockQuantity(bookId);
		if (instockQuantity < quantity) throw new IllegalArgumentException(("Not enough instock quantity"));
		balance -= bookPrice * quantity;
		bookDao.updateBalance(userId, balance);
		bookDao.updateStock(bookId, quantity);
		System.out.println(String.format("User %s just bought %s books with book ID: %s",
				userId, quantity, bookId));

		new FileInputStream("aaa.txt");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updatePrice(int bookId, double price) {
		bookDao.updatePrice(bookId, price);
		//int i = 1 / 0;
	}

	@Transactional
	public void mult() {
		buyBook(1, 1, 1);
		updatePrice(1, 2);
		int i = 1/0;
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
