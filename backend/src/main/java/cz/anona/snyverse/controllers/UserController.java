package cz.anona.snyverse.controllers;
import cz.anona.snyverse.entities.neo.state.State;
import cz.anona.snyverse.entities.neo.user.User;
import cz.anona.snyverse.entities.neo.state.StateCode;
import cz.anona.snyverse.services.SessionService;
import cz.anona.snyverse.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(	value = "List users", notes = "Check and create article or retrieve error")
    @GetMapping(path = "/all")
    public Iterable<User> getAllUsers() {
        return this.userService.getAll();
    }

    @ApiOperation(	value = "Create user (registration)", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public State registerUser(@RequestBody User user) {
        return this.userService.registerUser(user);
    }

    @ApiOperation(	value = "Try login", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public State loginUser(@RequestBody User user) {
        logger.warn(user.toString());
        State state = this.userService.loginUser(user);
        if(state.getStateCode() == StateCode.LOGGED) {
            logger.info(sessionService.getSession().toString());
        }
        return state;
    }

    @ApiOperation(	value = "Retrieve info about logged user", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public boolean isLogged(HttpSession session) {
        return this.sessionService.isLogged();
    }

    @ApiOperation(	value = "Get specified user", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        if(userId != null && userId > -1) {
            if(this.sessionService.isLogged() && this.userService.getUserFromSession().getId().equals(userId)) {
                return this.userService.getPrivateUser(userId);
            } else {
                return this.userService.getPublicUser(userId);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.valueOf(404));
        }
    }

}
