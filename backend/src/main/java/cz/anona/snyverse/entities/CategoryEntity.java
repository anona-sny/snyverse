package cz.anona.snyverse.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "category_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
	@SequenceGenerator(name = "category_seq_gen", sequenceName = "category_id_seq", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="parent")
	private CategoryEntity parentCategory;

	@OneToMany(mappedBy="parentCategory")
	private List<CategoryEntity> children;

}