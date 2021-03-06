package cz.anona.snyverse.unit;

import cz.anona.snyverse.entities.UserLoginEntity;
import cz.anona.snyverse.repositories.UserLoginRepository;
import cz.anona.snyverse.repositories.UserRepository;
import cz.anona.snyverse.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByUsername() {
        UserLoginEntity admin = this.userLoginRepository.getByUsername("administrator");
        Assertions.assertNotNull(admin);
        Assertions.assertEquals("administrator", admin.getUsername());
    }

    @Test
    public void testGetUserByInvalidUsername() {
        UserLoginEntity admin = this.userLoginRepository.getByUsername("sdf4gv65d4v654d654v4sad56f4g6v54d");
        Assertions.assertNull(admin);
    }



}
