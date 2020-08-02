package cz.anona.snyverse.entities.neo.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.anona.snyverse.entities.neo.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.OffsetDateTime;
import java.util.List;

@NodeEntity
@Data
@EqualsAndHashCode(exclude="article")
public class ArticleHistory {

	@Id
	@GeneratedValue
	private Long id;

	@Relationship(type = "ARTICLE_TO_ARTICLEDATA")
	@JsonIgnore
	private Article article;

	private OffsetDateTime createDate;
	private OffsetDateTime lastUpdateDate;
	private OffsetDateTime validFrom;
	private OffsetDateTime validTo;

	@Relationship(type = "ARTICLE_TO_CATEGORY")
	private Category category;
	private String header;
	private String body;

	@Relationship(type = "ARTICLE_TO_TAG")
	private List<Tag> tags;
}
