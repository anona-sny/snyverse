package cz.anona.snyverse.entities;

import cz.anona.snyverse.entities.enums.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "company_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq_gen")
	@SequenceGenerator(name = "company_seq_gen", sequenceName = "company_id_seq", allocationSize = 1)
	private Long id;

	@Column(unique = true)
	private String name;

	@Column(nullable = false)
	private CountryCode country;


}