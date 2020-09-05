package cz.anona.snyverse.dtos;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String acronym;

}
