package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.neo.state.State;
import cz.anona.snyverse.entities.neo.user.User;
import cz.anona.snyverse.entities.neo.state.StateCode;
import cz.anona.snyverse.entities.neo.user.UserType;
import cz.anona.snyverse.repositories.neo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected SessionService sessionService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public Iterable<User> getAll() {
        return this.userRepository.findAll();
    }

    public State registerUser(User u) {
        State state = State.getStatus("", "", StateCode.BAD_DATA);
        if(u == null) {
            state.setHeader("User object is null");
            return state;
        }
        List<User> existing = this.userRepository.findAllByUsername(u.getActiveData().getUsername());
        if(existing.size() > 0) {
            state.setHeader("User with username already exist");
            return state;
        }
        if(!RegexService.checkUsername(u.getActiveData().getUsername())) {
            state.setHeader("Username not valid");
            return state;
        } else if(!RegexService.checkEmail(u.getActiveData().getEmail())) {
            state.setHeader("Email not valid");
            return state;
        } else if(!RegexService.checkDisplayName(u.getActiveData().getUsername())) {
            state.setHeader("Display name not valid");
            return state;
        } else if(!RegexService.checkPassword(u.getActiveData().getPasswordHash())) {
            state.setHeader("Password is weak");
            return state;
        }
        // valid
        u.setArticles(null);
        u.getActiveData().setType(UserType.UNVERIFIED);
        u.getActiveData().setPasswordHash(this.generatePasswordHash(u.getActiveData().getPasswordHash()));
        this.userRepository.save(u);
        state.setHeader("User created");
        state.setBody("User is created, reload and login");
        state.setStateCode(StateCode.CREATED);
        return state;
    }

    public State loginUser(User user) {
        State state = State.getStatus("", "", StateCode.BAD_DATA);
        if(user == null || user.getActiveData().getUsername().equals("")) {
            return State.getStatus("Not logged", "User cannot be empty", StateCode.BAD_DATA);
        }
        List<User> existing = this.userRepository.findAllByUsername(user.getActiveData().getUsername());
        if(existing.size() == 0) {
            return State.getStatus("Not logged", "User not exist", StateCode.BAD_LOGIN);
        }
        User inDb = existing.get(0);
        if(generatePasswordHash(user.getActiveData().getPasswordHash()).equals(inDb.getActiveData().getPasswordHash())) {
            this.sessionService.associateSession(inDb.getId());
            return State.getStatus("Logged", "Logged", StateCode.LOGGED);
        } else {
            return State.getStatus("Not logged", "Bad password", StateCode.BAD_LOGIN);
        }
    }

    public User getUserFromSession() {
        if(this.sessionService.isLogged())
            return this.userRepository.findById(this.sessionService.getSession().getUser()).get();
        return null;
    }

    public ResponseEntity<User>  getPublicUser(Long id) {
        ResponseEntity<User> responseEntity;
        if(this.userRepository.existsById(id)) {
            User user = this.userRepository.findById(id).get();
            User copy = new User();
            copy.setArticles(user.getArticles());
            copy.setId(user.getId());
            copy.getActiveData().setUsername(user.getActiveData().getUsername());
            responseEntity = new ResponseEntity<>(copy, HttpStatus.valueOf(200));
        } else {
            logger.error("User dont exist with id: "+ id);
            responseEntity = new ResponseEntity<>(HttpStatus.valueOf(400));
        }
        return responseEntity;
    }

    public ResponseEntity<User> getPrivateUser(Long id) {
        ResponseEntity<User> responseEntity;
        if(this.userRepository.existsById(id)) {
            User user = this.userRepository.findById(id).get();
            User copy = new User();
            copy.setArticles(user.getArticles());
            copy.setId(user.getId());
            copy.getActiveData().setUsername(user.getActiveData().getUsername());
            copy.getActiveData().setEmail(user.getActiveData().getEmail());
            responseEntity = new ResponseEntity<>(copy, HttpStatus.valueOf(200));
        } else {
            logger.error("User dont exist with id: "+ id);
            responseEntity = new ResponseEntity<>(HttpStatus.valueOf(400));
        }
        return responseEntity;
    }

    private String generatePasswordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}