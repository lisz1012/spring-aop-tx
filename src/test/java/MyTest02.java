import com.lisz.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyTest02 {
	@Autowired
	private BookService service;

	@Test
	public void test01(){
		service.buyBook(1, 1, 2);
	}

	@Test
	public void test02(){
		service.buyBook02(1, 1, 1);
	}

	@Test
	public void test03(){
		service.buyBook03(1, 1, 1);
	}

	@Test
	public void test04(){
		service.buyBook04(1, 1, 1);
	}

	@Test
	public void test05(){
		service.buyBook05(1, 1, 1);
	}

	@Test
	public void test06() throws FileNotFoundException {
		service.buyBook06(1, 1, 1);
	}

	@Test
	public void test07() throws FileNotFoundException {
		service.buyBook07(1, 1, 1);
	}
}
