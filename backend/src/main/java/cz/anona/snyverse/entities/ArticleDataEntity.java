package cz.anona.snyverse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Table(name = "article_data_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_data_seq_gen")
	@SequenceGenerator(name = "article_data_seq_gen", sequenceName = "article_data_id_gen", allocationSize = 1)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="article_id")
	private ArticleEntity article;

	@Column(nullable = false)
	private String name;

	@Column
	private String body;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="author_id")
	private UserEntity author;

	@Column
	private OffsetDateTime createDate;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="category_id")
	private CategoryEntity category;

	@OneToMany(mappedBy = "articleData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArticleTagEntity> articleTagEntities;

	@OneToMany(mappedBy = "articleData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArticleFileEntity> articleFileEntities;

}