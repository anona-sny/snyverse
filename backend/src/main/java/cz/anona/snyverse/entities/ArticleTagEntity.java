package cz.anona.snyverse.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "article_tag_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTagEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_tag_seq_gen")
	@SequenceGenerator(name = "article_tag_seq_gen", sequenceName = "article_tag_id_gen", allocationSize = 1)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="article_data_id")
	private ArticleDataEntity articleData;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="tag_id")
	private TagEntity tag;



}