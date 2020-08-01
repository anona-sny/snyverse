package cz.anona.snyverse.entities.neo.article;

import cz.anona.snyverse.entities.neo.user.User;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
public class Tag {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Relationship(type = "TAG_TO_AUTHOR")
	private User author;

}
