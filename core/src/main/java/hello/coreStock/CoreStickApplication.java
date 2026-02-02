package hello.coreStock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreStickApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreStickApplication.class, args);
		System.out.println("Stock test");
	}

}
