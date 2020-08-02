package cz.anona.snyverse.controllers.dtos;

import lombok.Data;

@Data
public class RegistrationUserDTO {

    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;

}
