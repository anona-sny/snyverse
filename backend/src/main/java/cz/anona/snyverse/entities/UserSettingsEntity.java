package cz.anona.snyverse.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "user_settings_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsEntity {

	public static final int FONT_SIZE = 12;
	public static final String SCHEMA = "default";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_settings_seq_gen")
	@SequenceGenerator(name = "user_settings_seq_gen", sequenceName = "users_login_seq", allocationSize = 1)
	private Long id;

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;

	@Column
	private String schema;

	@Column
	private int fontSize;


}