package cz.anona.snyverse.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Data
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long userId;
    public String session;
    private Date lastAccess;

}
