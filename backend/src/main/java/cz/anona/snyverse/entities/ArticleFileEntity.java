package cz.anona.snyverse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "article_file_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_file_seq_gen")
	@SequenceGenerator(name = "article_file_seq_gen", sequenceName = "article_file_id_gen", allocationSize = 1)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="article_data_id")
	private ArticleDataEntity articleData;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="file_id")
	private FileEntity file;



}