package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.UserEntity;
import cz.anona.snyverse.entities.enums.UserType;
import cz.anona.snyverse.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptoService cryptoService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public void createAdmin() {
        if(this.userRepository.existsById(1l) || this.userRepository.findByUsername("anona") != null) {
            logger.info("Admin already exist");
        } else {
            UserEntity admin = new UserEntity();
            admin.setUsername("anona");
            admin.setType(UserType.ADMIN);
            admin.setEmail("frantisekzavazal@seznam.cz");
            admin.setPasswordHash(cryptoService.generatePasswordHash("f1a2n3d4a5A"));
            this.userRepository.save(admin);
            logger.info("Admin created");
        }
    }

}
