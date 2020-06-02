package cz.anona.snyverse;

import cz.anona.snyverse.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SnyverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnyverseApplication.class, args);
	}

}
