package cz.anona.snyverse.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "user_login_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_login_seq_gen")
	@SequenceGenerator(name = "user_login_seq_gen", sequenceName = "user_login_seq", allocationSize = 1)
	private Long id;

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
	private UserEntity user;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column
	private String passwordHash;

}