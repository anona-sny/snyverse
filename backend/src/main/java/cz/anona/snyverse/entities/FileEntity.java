package cz.anona.snyverse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Table(name = "file_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq_gen")
	@SequenceGenerator(name = "file_seq_gen", sequenceName = "file_id_seq", allocationSize = 1)
	private Long id;

	/**
	 * Is hex name without suffix (.xxx)
	 */
	@Column(unique = true)
	private String name;

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "author", referencedColumnName = "id", nullable = false)
	private UserEntity author;

	/**
	 * Size in bytes
	 */
	@Column
	private Long size;

	@Column
	private OffsetDateTime createDate;

	@Column
	private String suffix;

	@OneToMany(mappedBy = "file", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArticleFileEntity> articleFileEntities;

}