package cz.anona.snyverse.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserSchoolDTO {

    private Long id;
    @JsonBackReference
    private UserDTO user;
    private SchoolDTO school;
    private OffsetDateTime studiedFrom;
    private OffsetDateTime studiedTo;

}
