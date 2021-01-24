package cz.anona.snyverse.entities;

import cz.anona.snyverse.entities.enums.CountryCode;
import cz.anona.snyverse.entities.enums.LanguageCode;
import cz.anona.snyverse.entities.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "user_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
	@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_id_seq", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType type;

	@Column(nullable = false)
	private String name;

	@Column
	@Enumerated(EnumType.STRING)
	private CountryCode country;

	@Column
	@Enumerated(EnumType.STRING)
	private LanguageCode language;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserLoginEntity loginEntity;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserSettingsEntity settingsEntity;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserCompanyEntity> companyEntities;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArticleEntity> articleEntities;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArticleDataEntity> articleDataEntities;

}