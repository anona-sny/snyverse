package cz.anona.snyverse.entities.neo.article;

import cz.anona.snyverse.entities.neo.user.User;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@NodeEntity
@Data
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "ARTICLE_TO_ARTICLEDATA")
    private ArticleHistory activeData;

    @Required
    @Relationship(type = "WRITED", direction = Relationship.INCOMING)
    private User author;

}
