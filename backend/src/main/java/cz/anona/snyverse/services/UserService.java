package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.Status;
import cz.anona.snyverse.entities.User;
import cz.anona.snyverse.repositories.SessionRepository;
import cz.anona.snyverse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public SessionService sessionService;

    public Iterable<User> getAll() {
        return this.userRepository.findAll();
    }

    public Status registerUser(User u) {
        if(u == null) {
            return Status.getInvalidDTO("User object is null");
        }
        List<User> existing = this.userRepository.findAllByUsername(u.getUsername());
        if(existing.size() > 0) {
            return Status.getInvalidDTO("User with username already exist");
        }
        if(!RegexService.checkUsername(u.getUsername())) {
            return Status.getInvalidDTO("Username not valid");
        } else if(!RegexService.checkEmail(u.getEmail())) {
            return Status.getInvalidDTO("Email not valid");
        } else if(!RegexService.checkDisplayName(u.getDisplayName())) {
            return Status.getInvalidDTO("Display name not valid");
        } else if(!RegexService.checkPassword(u.getPassword())) {
            return Status.getInvalidDTO("Password is weak");
        }
        // valid
        u.setArticles(null);
        u.setPassword(this.generatePasswordHash(u.getPassword()));
        this.userRepository.save(u);
        Status status = new Status();
        status.setCode(200);
        status.setHeader("User created");
        status.setBody("User is created, reload and login");
        return status;
    }

    public Status loginUser(User user) {
        if(user == null) {
            return Status.getErrorStatus("Not logged", "User cannot be empty");
        }
        List<User> existing = this.userRepository.findAllByUsername(user.getUsername());
        if(existing.size() == 0) {
            return Status.getErrorStatus("Not logged", "User not exist");
        }
        User inDb = existing.get(0);
        if(generatePasswordHash(user.getPassword()).equals(inDb.getPassword())) {
            this.sessionService.associateSession(inDb.getId());
            return Status.getSuccessStatus("Logged", "Logged");
        } else {
            return Status.getErrorStatus("Not logged", "Bad password");
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