package cz.anona.snyverse.entities.neo;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.time.OffsetDateTime;

@NodeEntity
@Data
public class StoredSession {

    @Id
    @GeneratedValue
    private Long id;

    private Long user;
    private String session;
    private OffsetDateTime lastAccess;

}
