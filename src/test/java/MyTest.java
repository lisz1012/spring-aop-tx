import com.alibaba.druid.pool.DruidDataSource;
import com.lisz.dao.AccountDao;
import com.lisz.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MyTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

	@Autowired
	private AccountDao accountDao;

	@Test
	public void test() throws Exception {
		DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
		System.out.println(dataSource.getConnection());
		JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
		System.out.println(jdbcTemplate);
	}

	@Test
	public void test02(){
		JdbcTemplate template = context.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = "insert into account values(?, ?, ?, ?, ?, ?, ?, ?)";
		int rows = template.update(sql, null, "wanngxin", "abc", "xinxin", 20, "Jinan", "user", "aaa");
		System.out.println(rows + " rows have been created");
	}

	@Test
	public void test03(){
		JdbcTemplate template = context.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = "insert into account values(?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> list = new ArrayList<>();
		list.add(new Object[]{null, "Zhangsan", "xyz", "sansan", 22, "Qiandao", "user", "bbb"});
		list.add(new Object[]{null, "Lisi", "asd", "lisi", 22, "Qiandao", "user", "ccc"});
		list.add(new Object[]{null, "Wangwu", "qwe", "www", 21, "Beijing", "user", "ddd"});
		int rows[] = template.batchUpdate(sql, list);
		for (int row : rows) {
			System.out.println(row + " rows have been created");
		}
	}

	@Test
	public void test04(){
		JdbcTemplate template = context.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = "delete from account where id = ?";
		int row = template.update(sql, 36);
		System.out.println(row + " rows have been created");
	}

	@Test
	public void test05(){
		JdbcTemplate template = context.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = "update account set nick_name = ? where id = ?";
		int row = template.update(sql, "haha", 35);
		System.out.println(row + " rows have been created");
	}

	@Test
	public void test06 () {
		JdbcTemplate template = context.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = "select * from account where id = ?";
		Account account = template.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), 1);
		System.out.println(account);
	}

	@Test
	public void test07 () {
		JdbcTemplate template = context.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = "select * from account where id = ? or id = ?";
		List<Account> accounts = template.query(sql, new BeanPropertyRowMapper<>(Account.class), 1, 33);
		accounts.forEach(System.out::println);
	}

	@Test
	public void test08() {
		JdbcTemplate template = context.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = "select * from account where role = ?";
		List<Account> accounts = template.query(sql, new BeanPropertyRowMapper<>(Account.class), "user");
		accounts.forEach(System.out::println);
	}

	@Test
	public void test09() {
		Account account = new Account();
		account.setUsername("Wangwu");
		account.setPassword("qwe");
		account.setNickName("www");
		account.setAge(22);
		account.setLocation("Beijing");
		account.setRole("user");
		account.setProfileUrl("ddd");
		accountDao.save(account);
	}
}
