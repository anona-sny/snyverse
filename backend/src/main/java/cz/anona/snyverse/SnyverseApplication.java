package cz.anona.snyverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SnyverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnyverseApplication.class, args);
	}

}
