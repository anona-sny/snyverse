package cz.anona.snyverse.entities;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
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
    private String password_hash;

    @Relationship(type = "WRITED")
    public List<Post> posts;

}
