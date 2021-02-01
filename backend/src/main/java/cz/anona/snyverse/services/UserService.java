package cz.anona.snyverse.services;

import cz.anona.snyverse.dtos.UserLoginDTO;
import cz.anona.snyverse.dtos.UserRegisterDTO;
import cz.anona.snyverse.entities.UserEntity;
import cz.anona.snyverse.entities.UserLoginEntity;
import cz.anona.snyverse.entities.UserSettingsEntity;
import cz.anona.snyverse.entities.enums.UserExceptionType;
import cz.anona.snyverse.entities.enums.UserType;
import cz.anona.snyverse.entities.exceptions.UserException;
import cz.anona.snyverse.repositories.UserLoginRepository;
import cz.anona.snyverse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final CryptoService cryptoService;
    private final LanguageCountryService languageCountryService;
    private final SessionService sessionService;

    @Autowired
    public UserService(UserRepository u1, UserLoginRepository u2, CryptoService c1, LanguageCountryService l1, SessionService s1) {
        this.userRepository = u1;
        this.userLoginRepository = u2;
        this.cryptoService = c1;
        this.languageCountryService = l1;
        this.sessionService = s1;
    }

    public UserEntity getUserByUsername(String username) {
        return this.userLoginRepository.getByUsername(username).getUser();
    }

    public UserEntity getUserById(Long id) {
        return this.userRepository.getOne(id);
    }

    @Transactional
    public void createUser(UserRegisterDTO userRegisterDTO) throws UserException {
        // regex username, password, email and name check
        if(userRegisterDTO.getUsername()==null||!RegexService.checkUsername(userRegisterDTO.getUsername())) {
            throw new UserException(UserExceptionType.USERNAME_INVALID, "Username not match to regex");
        }
        if(userRegisterDTO.getPassword()==null||!RegexService.checkPassword(userRegisterDTO.getPassword())) {
            throw new UserException(UserExceptionType.PASSWORD_INVALID, "Password not match to regex");
        }
        if(userRegisterDTO.getEmail()==null||!RegexService.checkEmail(userRegisterDTO.getEmail())) {
            throw new UserException(UserExceptionType.EMAIL_INVALID, "Email not match to regex");
        }
        if(userRegisterDTO.getName()==null||!RegexService.checkDisplayName(userRegisterDTO.getName())) {
            throw new UserException(UserExceptionType.NAME_INVALID, "Name not match to regex");
        }
        // username and email check
        if(this.userLoginRepository.getByUsername(userRegisterDTO.getUsername()) != null) {
            throw new UserException(UserExceptionType.USERNAME_OCCUPIED, "Username already exist");
        }
        if(this.userLoginRepository.getByEmail(userRegisterDTO.getEmail()) != null) {
            throw new UserException(UserExceptionType.EMAIL_OCCUPIED, "Email already exist");
        }
        // check country and language
        if(userRegisterDTO.getCountryCode() == null ||
                this.languageCountryService.getCountry(userRegisterDTO.getCountryCode()) == null) {
            throw new UserException(UserExceptionType.COUNTRY_INVALID, "Country code doesn't exist");
        }
        if(userRegisterDTO.getLanguageCode() == null ||
                this.languageCountryService.getLanguage(userRegisterDTO.getLanguageCode()) == null) {
            throw new UserException(UserExceptionType.LANGUAGE_INVALID, "Language code doesn't exist");
        }
        //create and save user
        UserEntity newUser = new UserEntity();                                                                          // main user
        newUser.setName(userRegisterDTO.getName());
        newUser.setCountry(this.languageCountryService.getCountry(userRegisterDTO.getCountryCode()));
        newUser.setLanguage(this.languageCountryService.getLanguage(userRegisterDTO.getLanguageCode()));
        newUser.setType(UserType.USER);
        UserLoginEntity newUserLogin = new UserLoginEntity();                                                           // login of account
        newUserLogin.setUsername(userRegisterDTO.getUsername());
        newUserLogin.setPasswordHash(this.cryptoService.generatePasswordHash(userRegisterDTO.getPassword()));
        newUserLogin.setEmail(userRegisterDTO.getEmail());
        newUserLogin.setUser(newUser);
        newUser.setLoginEntity(newUserLogin);
        UserSettingsEntity userSettingsEntity = new UserSettingsEntity();                                               // settings of account
        userSettingsEntity.setSchema("default");
        userSettingsEntity.setFontSize(12);
        userSettingsEntity.setUser(newUser);
        newUser.setSettingsEntity(userSettingsEntity);
        this.userRepository.save(newUser);
    }

    @Transactional
    public void loginUser(UserLoginDTO userLoginDTO) throws UserException {
        if(userLoginDTO!=null&&userLoginDTO.getUsername()!=null&&RegexService.checkUsername(userLoginDTO.getUsername())) {
            UserLoginEntity maybe = this.userLoginRepository.getByUsername(userLoginDTO.getUsername());
            if(maybe == null || !maybe.getPasswordHash().equals(this.cryptoService.generatePasswordHash(userLoginDTO.getPassword()))) {
                throw new UserException(UserExceptionType.INVALID_CREDENTIALS, "Account doesn't exist");
            } else {
                // user exist and password match
                this.sessionService.associateSession(maybe.getUser().getId());
            }
        } else {
            throw new UserException(UserExceptionType.USERNAME_INVALID, "Given username is null or have invalid chars");
        }
    }

    public void logoutUser() throws UserException {
        if(!this.sessionService.isLogged()) {
            throw new UserException(UserExceptionType.OTHER, "No users logged");
        }
        this.sessionService.dissociateSession(this.sessionService.getSession().getUserId());
    }

}
