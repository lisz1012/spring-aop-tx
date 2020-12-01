import com.lisz.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest04 {
	@Test
	public void test01() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BookService bean = context.getBean(BookService.class);
		bean.mult();
	}
}
