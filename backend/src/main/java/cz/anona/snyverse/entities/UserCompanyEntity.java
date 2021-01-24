package cz.anona.snyverse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Table(name = "user_company_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompanyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_company_seq_gen")
	@SequenceGenerator(name = "user_company_seq_gen", sequenceName = "user_company_id_seq", allocationSize = 1)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private UserEntity user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="company_id")
	private CompanyEntity company;

	@Column(name = "from_date")
	private OffsetDateTime from;

	@Column(name = "to_date")
	private OffsetDateTime to;

	@Column
	private String position;


}