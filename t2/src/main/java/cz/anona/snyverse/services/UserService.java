package cz.anona.snyverse.services;

import cz.anona.snyverse.dtos.UserDTO;
import cz.anona.snyverse.dtos.UserLoginDTO;
import cz.anona.snyverse.dtos.UserRegistrationDTO;
import cz.anona.snyverse.entities.UserEntity;
import cz.anona.snyverse.entities.enums.UserType;
import cz.anona.snyverse.entities.mappers.UserMapper;
import cz.anona.snyverse.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private SessionService sessionService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @EventListener(ApplicationReadyEvent.class)
    public void createAdmin() {
        if(this.userRepository.existsById(1L) || this.userRepository.findByUsername("anona") != null) {
            logger.info("Admin already exist");
        } else {
            UserEntity admin = new UserEntity();
            admin.setUsername("anona");
            admin.setType(UserType.ADMIN);
            admin.setEmail("frantisekzavazal@seznam.cz");
            admin.setPasswordHash("478dc6df9cbb2225452428dd5fd06ad75efbde43ff99159afe712ffcf6d57" +
                    "ca88dcf1c9c37f58e94cba545eeed88a07bf0e2ba88c0a15f15a0ed2e3dd44ecded");
            this.userRepository.save(admin);
            logger.info("Admin created");
        }
    }

    public ResponseEntity<String> login(UserLoginDTO userLoginDTO) {
        if(userLoginDTO == null) {
            return responseService.generateResponse(ResponseService.BADREQUEST, "No object");
        }
        UserEntity user;
        if(userLoginDTO.getUsername() != null) {
            user = this.userRepository.findByUsername(userLoginDTO.getUsername());
            if(user == null) {
                return responseService.generateResponse(ResponseService.BADREQUEST, "Bad username");
            }
        } else {
            if (userLoginDTO.getEmail() == null) {
                return responseService.generateResponse(ResponseService.BADREQUEST, "Email and username is empty");
            }
            user = this.userRepository.findByEmail(userLoginDTO.getEmail());
            if(user == null) {
                return responseService.generateResponse(ResponseService.BADREQUEST, "Bad email");
            }
        }
        if(userLoginDTO.getPassword() != null && this.cryptoService.generatePasswordHash(userLoginDTO.getPassword()).equals(user.getPasswordHash())) {
            this.sessionService.associateSession(user.getId());
            return this.responseService.generateResponse(ResponseService.OKREQUEST, "Logged");
        }
        return responseService.generateResponse(ResponseService.BADREQUEST, "Bad password");
    }

    public ResponseEntity<String> register(UserRegistrationDTO userRegistrationDTO) {
        if(userRegistrationDTO == null) {
            return responseService.generateResponse(ResponseService.BADREQUEST, "No object");
        }
        if(!RegexService.checkUsername(userRegistrationDTO.getUsername())) {
            return responseService.generateResponse(ResponseService.BADREQUEST, "Bad username");
        }
        if(!RegexService.checkPassword(userRegistrationDTO.getPassword())) {
            return responseService.generateResponse(ResponseService.BADREQUEST, "Bad password");
        }
        if(!RegexService.checkEmail(userRegistrationDTO.getEmail())) {
            return responseService.generateResponse(ResponseService.BADREQUEST, "Bad email");
        }
        // neexistuje email nebo username?
        if(this.userRepository.findByEmail(userRegistrationDTO.getEmail()) != null) {
            return responseService.generateResponse(ResponseService.BADREQUEST, "Email exist");
        }
        if(this.userRepository.findByUsername(userRegistrationDTO.getUsername()) != null) {
            return responseService.generateResponse(ResponseService.BADREQUEST, "Username exist");
        }
        UserEntity userEntity = this.userMapper.toEntityFromRegistration(userRegistrationDTO);
        userEntity.setPasswordHash(this.cryptoService.generatePasswordHash(userRegistrationDTO.getPassword()));
        UserEntity savedUser = this.userRepository.save(userEntity);
        if(savedUser.getId() != null) {
            return responseService.generateResponse(ResponseService.CREATED, "Registered");
        } else {
            return responseService.generateResponse(ResponseService.FATALERROR, "Fatal error on server, " +
                    "admin will be notified");
        }
    }

    public ResponseEntity<String> updateUser(UserDTO user) {
        if(user == null) {
            return responseService.generateResponse(ResponseService.BADREQUEST, "No object");
        }
        UserEntity userEntity = this.userRepository.getOne(user.getId());
        // TODO checking user entity before save
        return null;
    }

    public ResponseEntity<String> logout() {
        this.sessionService.dissociateSession(this.sessionService.getSession().getUserId());
        return this.responseService.generateResponse(ResponseService.OKREQUEST, "Logoff");
    }

    public UserEntity returnLoggedUser() {
        Optional<UserEntity> userO = this.userRepository.findById(this.sessionService.getSession().getUserId());
        return userO.orElse(null);
    }
}
