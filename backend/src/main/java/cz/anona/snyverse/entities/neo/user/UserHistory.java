package cz.anona.snyverse.entities.neo.user;

import cz.anona.snyverse.entities.neo.article.Article;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.OffsetDateTime;
import java.util.List;

@NodeEntity
@Data
public class UserHistory {

	@Id
	@GeneratedValue
	private Long id;

	@Relationship(type = "USERDATA_TO_USER")
	private User user;

	private String username;
	private String email;
	private UserType type;
	private String passwordHash;
	private String name;
	private String surname;
	private String acronym;
	private String profilePhoto;
	private OffsetDateTime valid_from;
	private OffsetDateTime valid_to;

	@Relationship(type = "IN_SCHOOLS")
	public List<School> schools;

	@Relationship(type = "IN_WORKS")
	public List<Work> works;
}
