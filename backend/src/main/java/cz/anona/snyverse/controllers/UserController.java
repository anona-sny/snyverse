package cz.anona.snyverse.controllers;

import cz.anona.snyverse.controllers.dtos.StatusDTO;
import cz.anona.snyverse.controllers.dtos.UserRegisterDTO;
import cz.anona.snyverse.entities.UserEntity;
import cz.anona.snyverse.entities.enums.StatusType;
import cz.anona.snyverse.entities.exceptions.UserException;
import cz.anona.snyverse.services.SessionService;
import cz.anona.snyverse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public UserController(SessionService s1, UserService u1) {
        this.sessionService = s1;
        this.userService = u1;
    }

    @RequestMapping(path = "/isLogged", method = RequestMethod.GET)
    public ResponseEntity<String> isLogged() {
        if(this.sessionService.isLogged()) {
            return ResponseEntity.ok().body("Logged");
        } else {
            return ResponseEntity.ok().body("Not logged");
        }
    }

    @RequestMapping(path = "/login")
    public ResponseEntity<Object> login() {
        return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * Create new user if is valid, set him default settings
     * else throw Exception
     * @param userRegisterDTO
     * @return
     */
    @RequestMapping(path = "/register")
    public ResponseEntity<StatusDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) throws UserException {
        this.userService.createUser(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StatusDTO.builder().result(StatusType.CREATED).message("User was successfully registered").build());
    }

    @ExceptionHandler({ UserException.class })
    public ResponseEntity<Object> handleException(UserException exception) {
        return ResponseEntity.status(500).body(exception.getData());
    }

}
