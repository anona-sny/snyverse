package cz.anona.snyverse.dtos;

import cz.anona.snyverse.entities.enums.CountryCode;
import cz.anona.snyverse.entities.enums.LanguageCode;
import cz.anona.snyverse.entities.enums.UserType;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String name;
    private UserType userType;
    private List<CompanyDTO> companies;
    private CountryCode country;
    private LanguageCode language;

}
