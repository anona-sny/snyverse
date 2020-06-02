package cz.anona.snyverse.controllers;

import cz.anona.snyverse.entities.User;
import cz.anona.snyverse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping(path = "/users")
    public Iterable<User> getAllUsers() {
        return this.userService.getAll();
    }

    @PostMapping(path = "/user")
    public User saveUser(@RequestBody User user) {
        return this.userService.saveUser(user);
    }

}
