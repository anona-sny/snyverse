package cz.anona.snyverse.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "tag_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq_gen")
	@SequenceGenerator(name = "tag_seq_gen", sequenceName = "tag_id_gen", allocationSize = 1)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserEntity author;

	@OneToMany(mappedBy = "articleData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArticleTagEntity> articleTagEntities;


}