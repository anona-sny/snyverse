package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.neo.state.State;
import cz.anona.snyverse.entities.neo.User;
import cz.anona.snyverse.entities.neo.state.StateCode;
import cz.anona.snyverse.repositories.neo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Iterable<User> getAll() {
        return this.userRepository.findAll();
    }

    public State registerUser(User u) {
        State state = State.getStatus("", "", StateCode.BAD_DATA);
        if(u == null) {
            state.setHeader("User object is null");
            return state;
        }
        List<User> existing = this.userRepository.findAllByUsername(u.getUsername());
        if(existing.size() > 0) {
            state.setHeader("User with username already exist");
            return state;
        }
        if(!RegexService.checkUsername(u.getUsername())) {
            state.setHeader("Username not valid");
            return state;
        } else if(!RegexService.checkEmail(u.getEmail())) {
            state.setHeader("Email not valid");
            return state;
        } else if(!RegexService.checkDisplayName(u.getDisplayName())) {
            state.setHeader("Display name not valid");
            return state;
        } else if(!RegexService.checkPassword(u.getPassword())) {
            state.setHeader("Password is weak");
            return state;
        }
        // valid
        u.setArticles(null);
        u.setPassword(this.generatePasswordHash(u.getPassword()));
        this.userRepository.save(u);
        state.setHeader("User created");
        state.setBody("User is created, reload and login");
        state.setStateCode(StateCode.CREATED);
        return state;
    }

    public State loginUser(User user) {
        State state = State.getStatus("", "", StateCode.BAD_DATA);
        if(user == null || user.getUsername().equals("")) {
            return State.getStatus("Not logged", "User cannot be empty", StateCode.BAD_DATA);
        }
        List<User> existing = this.userRepository.findAllByUsername(user.getUsername());
        if(existing.size() == 0) {
            return State.getStatus("Not logged", "User not exist", StateCode.BAD_LOGIN);
        }
        User inDb = existing.get(0);
        if(generatePasswordHash(user.getPassword()).equals(inDb.getPassword())) {
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