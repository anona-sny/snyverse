package cz.anona.snyverse.entities.neo.user;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.time.OffsetDateTime;

@RelationshipEntity(type = "IN_WORKS")
@Data
public class UserWorkRelation {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;

    @EndNode
    private Work work;

    private OffsetDateTime from;
    private OffsetDateTime to;
    private String workplace;
}
