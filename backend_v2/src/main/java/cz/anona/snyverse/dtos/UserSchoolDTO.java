package cz.anona.snyverse.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Date;
import java.time.OffsetDateTime;

@Data
public class UserSchoolDTO {

    @JsonBackReference
    private UserDTO user;
    private SchoolDTO school;
    private Date studiedFrom;
    private Date studiedTo;

}
