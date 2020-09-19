package cz.anona.snyverse.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.sql.Date;

@Data
public class UserWorkDTO {

    @JsonBackReference
    private UserDTO user;
    private WorkDTO work;
    private Date workedFrom;
    private Date workedTo;

}
