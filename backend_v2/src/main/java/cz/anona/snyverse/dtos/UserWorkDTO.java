package cz.anona.snyverse.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class UserWorkDTO {

    private Long id;
    @JsonBackReference
    private UserDTO user;
    private WorkDTO work;
    private OffsetDateTime workedFrom;
    private OffsetDateTime workedTo;

}
