package cz.anona.snyverse.entities.neo.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.anona.snyverse.entities.neo.article.Article;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

import java.util.List;

@NodeEntity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "USER_TO_USERDATA")
    private UserHistory activeData;

    @Relationship(type = "WRITED")
    public List<Article> articles;

}
