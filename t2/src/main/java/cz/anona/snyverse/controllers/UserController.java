package cz.anona.snyverse.controllers;

import cz.anona.snyverse.dtos.UserDTO;
import cz.anona.snyverse.dtos.UserLoginDTO;
import cz.anona.snyverse.dtos.UserRegistrationDTO;
import cz.anona.snyverse.services.ResponseService;
import cz.anona.snyverse.services.SessionService;
import cz.anona.snyverse.services.UserService;
import cz.anona.snyverse.services.guards.OperationGuard;
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

    @Autowired
    private OperationGuard guard;

    @Autowired
    private ResponseService responseService;

    @RequestMapping(path = "/isLogged", method = RequestMethod.GET)
    public ResponseEntity<String> isLogged() {
        if(this.sessionService.isLogged()) {
            return ResponseEntity.ok().body("Logged");
        } else {
            return ResponseEntity.ok().body("Not logged");
        }
    }

    @ApiOperation(value = "Registering new user", notes = "Registering user by inputted data in form")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationDTO user) {
        return this.userService.register(user);
    }
    @RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDTO user) {
        if (!this.guard.checkingPermissionUser(userId)) {
            return this.responseService.generateResponse(ResponseService.NOTAUTHORIZED, "not authorized");
        }
        user.setId(userId);
        return this.userService.updateUser(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO user) {
        return this.userService.login(user);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<String> logoutUser() {
        return this.userService.logout();
    }

}
