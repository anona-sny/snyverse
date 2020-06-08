package cz.anona.snyverse.entities.jpa;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity(name = "session")
@Table(name = "session")
@Data
public class Session {

    @Id
    @GeneratedValue
    public Long id;
    public Long user;
    public String session;
    private OffsetDateTime lastAccess;

}
