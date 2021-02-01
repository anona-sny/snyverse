package cz.anona.snyverse.controllers;

import cz.anona.snyverse.dtos.StatusDTO;
import cz.anona.snyverse.dtos.UserDTO;
import cz.anona.snyverse.dtos.UserLoginDTO;
import cz.anona.snyverse.dtos.UserRegisterDTO;
import cz.anona.snyverse.dtos.mappings.UserMapping;
import cz.anona.snyverse.entities.UserEntity;
import cz.anona.snyverse.entities.enums.StatusType;
import cz.anona.snyverse.entities.enums.UserExceptionType;
import cz.anona.snyverse.entities.exceptions.UserException;
import cz.anona.snyverse.services.SessionService;
import cz.anona.snyverse.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final SessionService sessionService;
    private final UserService userService;
    private final UserMapping userMapping;

    @Autowired
    public UserController(SessionService s1, UserService u1, UserMapping u2) {
        this.sessionService = s1;
        this.userService = u1;
        this.userMapping = u2;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<StatusDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) throws UserException {
        this.userService.createUser(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StatusDTO.builder().result(StatusType.CREATED).message("User was successfully registered").build());
    }

    @RequestMapping(path = "/login")
    public ResponseEntity<StatusDTO> login(@RequestBody UserLoginDTO userLoginDTO) throws UserException {
        this.userService.loginUser(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(StatusDTO.builder().result(StatusType.LOGGED)
                .message("User was logged").build());
    }

    @RequestMapping(path = "/logout")
    public ResponseEntity<StatusDTO> logout() throws UserException {
        this.userService.logoutUser();
        return ResponseEntity.status(HttpStatus.OK).body(StatusDTO.builder().result(StatusType.LOGGED_OUT)
                .message("User was logged out").build());
    }

    @GetMapping(path = "/isLogged")
    public ResponseEntity<StatusDTO> isLogged() {
        if(this.sessionService.isLogged()) {
            return ResponseEntity.ok().body(StatusDTO.builder().result(StatusType.LOGGED)
                    .message("User is logged").build());
        } else {
            return ResponseEntity.ok().body(StatusDTO.builder().result(StatusType.LOGGED_OUT)
                    .message("User is not logged").build());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) throws UserException {
        UserEntity userEntity = this.userService.getUserById(userId);
        if(userEntity == null) {
            throw new UserException(UserExceptionType.USER_DOESNT_EXIST, "User with given id doesnt exist");
        }
        UserDTO userDTO = this.userMapping.toDTO(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @ExceptionHandler({ UserException.class })
    public ResponseEntity<Object> handleException(UserException exception) {
        return ResponseEntity.status(500).body(exception.getData());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleFatalException(Exception e) {
        log.error(e.getLocalizedMessage());
        return ResponseEntity.status(500).body("Fatal exception on server");
    }

}
