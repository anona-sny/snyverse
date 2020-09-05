package cz.anona.snyverse.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class UserSchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;
    @Column(name = "studied_from")
    private Date studiedFrom;
    @Column(name = "studied_to")
    private Date studiedTo;
    // enum na granted diploma

}
