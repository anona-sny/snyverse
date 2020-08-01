package cz.anona.snyverse.entities.neo.article;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
public class Category {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Relationship(type = "CATEGORY_TO_PARENT")
	private Category subcategoryOf;
}
