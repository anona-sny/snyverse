package cz.anona.snyverse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Table(name = "session_entity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_seq_gen")
	@SequenceGenerator(name = "session_seq_gen", sequenceName = "session_id_seq", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column
	private String session;

	@Column(nullable = false)
	private OffsetDateTime lastAccess;


}