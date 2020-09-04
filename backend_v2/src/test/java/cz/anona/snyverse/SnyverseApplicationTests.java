package cz.anona.snyverse;

import cz.anona.snyverse.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SnyverseApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		this.userService.createAdmin();
	}

}
