import com.lisz.service.BookService;
import com.lisz.service.MultiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyTest03 {
	@Autowired
	private MultiService multiService;

	@Autowired
	private BookService bookService;

	@Test
	public void test01(){
		multiService.mult();
	}

	@Test
	public void test02(){
		multiService.buyBook();
	}

	@Test
	public void test03(){
		bookService.mult();
	}
}
