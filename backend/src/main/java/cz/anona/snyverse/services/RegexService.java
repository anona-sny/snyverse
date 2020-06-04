package cz.anona.snyverse.services;

import org.springframework.stereotype.Service;

@Service
public class RegexService {

    private static final String usernameRegex = "^[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]$";
    private static final String displayNameRegex = "^[a-zA-Z0-9-_,.ěščřžýáíéúůňďťĚŠČŘŽÝÁÍÉÚŮŇĎŤ]*$";
    private static final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

    public static boolean checkUsername(String username) {
        return username.matches(RegexService.usernameRegex);
    }
    public static boolean checkDisplayName(String displayName) {
        return displayName.matches(RegexService.displayNameRegex);
    }
    public static boolean checkEmail(String email) {
        return email.matches(RegexService.emailRegex);
    }
    public static boolean checkPassword(String password) {
        return password.matches(RegexService.passwordRegex);
    }

}