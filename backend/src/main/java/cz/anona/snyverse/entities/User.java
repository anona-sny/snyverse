package cz.anona.snyverse.entities;

import cz.anona.snyverse.entities.article.Article;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

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

    @Relationship(type = "WRITED")
    public List<Article> articles;

}
