package cz.anona.snyverse.controllers;
import cz.anona.snyverse.entities.Status;
import cz.anona.snyverse.entities.User;
import cz.anona.snyverse.services.SessionService;
import cz.anona.snyverse.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public SessionService sessionService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(path = "/users")
    public Iterable<User> getAllUsers() {
        return this.userService.getAll();
    }

    @RequestMapping(path = "/user/create", method = RequestMethod.POST)
    public Status registerUser(@RequestBody User user) {
        return this.userService.registerUser(user);
    }

    @RequestMapping(path = "/user/login", method = RequestMethod.POST)
    public Status loginUser(@RequestBody User user) {
        Status status = this.userService.loginUser(user);
        logger.warn(sessionService.getSession().toString());
        return status;
    }

    @RequestMapping(path = "/user/info", method = RequestMethod.GET)
    public String isLogged(HttpSession session) {
        if(this.sessionService.isLogged()) {
            logger.warn(this.sessionService.getSession().toString());
            return "Logged";
        } else {
            return "User not logged";
        }
    }

}
