package cz.anona.snyverse.controllers;
import cz.anona.snyverse.entities.neo.state.State;
import cz.anona.snyverse.entities.neo.User;
import cz.anona.snyverse.entities.neo.state.StateCode;
import cz.anona.snyverse.services.SessionService;
import cz.anona.snyverse.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public SessionService sessionService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(path = "/all")
    public Iterable<User> getAllUsers() {
        return this.userService.getAll();
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public State registerUser(@RequestBody User user) {
        return this.userService.registerUser(user);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public State loginUser(@RequestBody User user) {
        logger.warn(user.toString());
        State state = this.userService.loginUser(user);
        if(state.getStateCode() == StateCode.LOGGED) {
            logger.info(sessionService.getSession().toString());
        }
        return state;
    }

    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public String isLogged(HttpSession session) {
        if(this.sessionService.isLogged()) {
            logger.warn(this.sessionService.getSession().toString());
            return "Logged";
        } else {
            return "User not logged";
        }
    }

}
