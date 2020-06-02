package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.User;
import cz.anona.snyverse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public Iterable<User> getAll() {
        return this.userRepository.findAll();
    }

    public User saveUser(User u) {
        return this.userRepository.save(u);
    }

}