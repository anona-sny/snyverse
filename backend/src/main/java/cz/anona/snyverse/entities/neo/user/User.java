package cz.anona.snyverse.entities.neo.user;

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
    private String username;
    private String email;
    private String displayName;
    private String password;
    private UserType type;

    @Relationship(type = "WRITED")
    public List<Article> articles;

}
