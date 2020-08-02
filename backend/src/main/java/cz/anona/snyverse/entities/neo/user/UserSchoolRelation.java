package cz.anona.snyverse.entities.neo.user;

import cz.anona.snyverse.entities.neo.article.Article;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.time.OffsetDateTime;

@RelationshipEntity(type = "IN_SCHOOLS")
@Data
public class UserSchoolRelation {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;

    @EndNode
    private School school;

    private OffsetDateTime from;
    private OffsetDateTime to;
}
