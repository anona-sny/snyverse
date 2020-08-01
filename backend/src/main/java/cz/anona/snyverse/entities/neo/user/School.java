package cz.anona.snyverse.entities.neo.user;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
public class School {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String url;
	private String city;
	private String country;
}
