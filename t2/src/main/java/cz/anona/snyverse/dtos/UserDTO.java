package cz.anona.snyverse.dtos;

import cz.anona.snyverse.entities.enums.UserType;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String acronym;
    private String profilePhoto;
    private Date createdDate;
    private Date updateDate;
    private UserType type;
    private List<UserSchoolDTO> schools;
    private List<UserWorkDTO> works;
    private List<ArticleDTO> articles;

}
