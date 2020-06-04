package cz.anona.snyverse.entities;

import cz.anona.snyverse.entities.article.Article;
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
