package cz.anona.snyverse.entities.neo.article;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "INCLUDED_IN")
@Data
public class IncludedInRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Article article;

    @EndNode
    private Tag tag;

}
