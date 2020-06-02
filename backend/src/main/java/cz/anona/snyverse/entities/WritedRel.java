package cz.anona.snyverse.entities;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.time.OffsetDateTime;

@RelationshipEntity(type = "WRITED")
@Data
public class WritedRel {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;

    @EndNode
    private Post post;
    private OffsetDateTime date;

}
