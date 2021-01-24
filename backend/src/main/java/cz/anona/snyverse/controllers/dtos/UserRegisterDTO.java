package cz.anona.snyverse.controllers.dtos;

import lombok.Data;

@Data
public class UserRegisterDTO {

    private String username;
    private String password;
    private String email;
    private String name;
    private String countryCode;
    private String languageCode;

}
