package cz.anona.snyverse.serializeandstore;

import cz.anona.snyverse.controllers.dtos.RegistrationUserDTO;
import cz.anona.snyverse.entities.neo.user.User;
import cz.anona.snyverse.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

@SpringBootTest
public class BasicWorkTest {

    @Autowired
    private UserService userService;

    @Test
    public void tryBadRegister() {
        RegistrationUserDTO userR = new RegistrationUserDTO();
        userR.setEmail("frantisekzavazal@seznam.cz");
        userR.setPassword("sjdbfjhbsdjfhbsdf");
        userR.setUsername("anona"); // existing
        userR.setName("Fanda");
        userR.setSurname("Zavazal");
        ResponseEntity<?> responseEntity = this.userService.registerUser(userR);
        Assert.isTrue(responseEntity.getStatusCode() != HttpStatus.OK, "Uživatel nesmí být stejný");
    }


    private Long storeID;

    @Test
    public void trySuccessRegister() {
        RegistrationUserDTO userR = new RegistrationUserDTO();
        userR.setEmail("frantisekzavazal"+(int)(System.nanoTime())+"@seznam.cz");
        userR.setPassword("sjdbfjhbsdjfhbsdf");
        userR.setUsername("asdsadasd"+(int)(System.nanoTime())); // not existing
        userR.setName("Fanda");
        userR.setSurname("Zavazal");
        ResponseEntity<?> responseEntity = this.userService.registerUser(userR);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        this.storeID = ((User) (responseEntity.getBody())).getId();
    }

}
