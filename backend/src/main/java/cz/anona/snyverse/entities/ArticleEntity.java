package cz.anona.snyverse.entities;

import cz.anona.snyverse.entities.enums.LanguageCode;
import cz.anona.snyverse.entities.enums.VisibilityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Table(name = "article_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq_gen")
	@SequenceGenerator(name = "article_seq_gen", sequenceName = "article_id_gen", allocationSize = 1)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="author_id")
	private UserEntity author;

	@Column
	private OffsetDateTime creationDate;

	@Column
	@Enumerated(EnumType.STRING)
	private LanguageCode language;

	@Column(nullable = false)
	private VisibilityType visibility;

	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArticleDataEntity> articleDataEntities;

}