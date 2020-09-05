package cz.anona.snyverse.controllers;

import cz.anona.snyverse.dtos.UserDTO;
import cz.anona.snyverse.dtos.UserLoginDTO;
import cz.anona.snyverse.dtos.UserRegistrationDTO;
import cz.anona.snyverse.services.SessionService;
import cz.anona.snyverse.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(path = "/isLogged", method = RequestMethod.GET)
    public ResponseEntity isLogged() {
        if(this.sessionService.isLogged()) {
            return ResponseEntity.ok().body("Logged");
        } else {
            return ResponseEntity.ok().body("Not logged");
        }
    }

    @ApiOperation(value = "Registering new user", notes = "Registering user by inputted data in form")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserRegistrationDTO user) {
        return this.userService.register(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginUser(@RequestBody UserLoginDTO user) {
        return this.userService.login(user);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity logoutUser() {
        return this.userService.logout();
    }

}
