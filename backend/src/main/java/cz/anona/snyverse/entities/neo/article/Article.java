package cz.anona.snyverse.entities.neo.article;

import cz.anona.snyverse.entities.neo.User;
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

    @Required
    private String title;

    private OffsetDateTime creationDate;

    private OffsetDateTime updateDate;

    @Required
    @Relationship(type = "WRITED", direction = Relationship.INCOMING)
    private User author;

    @Relationship(type = "INCLUDED_IN")
    private List<Tag> tags;

    private String body;

}
