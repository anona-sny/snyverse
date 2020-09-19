package cz.anona.snyverse.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.sql.Date;

@Data
public class UserSchoolDTO {

    @JsonBackReference
    private UserDTO user;
    private SchoolDTO school;
    private Date studiedFrom;
    private Date studiedTo;

}
