package cz.anona.snyverse.entities.neo;

import cz.anona.snyverse.entities.neo.article.Article;
import cz.anona.snyverse.entities.neo.user.User;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.time.OffsetDateTime;

@RelationshipEntity(type = "WRITED")
@Data
public class WritedRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;

    @EndNode
    private Article article;
    private OffsetDateTime date;

}
