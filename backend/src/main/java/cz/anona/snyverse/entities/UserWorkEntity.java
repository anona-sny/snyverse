package cz.anona.snyverse.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class UserWorkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "work_id")
    private WorkEntity work;
    @Column(name = "worked_from")
    private Date workedFrom;
    @Column(name = "worked_to")
    private Date workedTo;

}
