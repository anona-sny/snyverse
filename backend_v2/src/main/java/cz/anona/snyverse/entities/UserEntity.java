package cz.anona.snyverse.entities;

import cz.anona.snyverse.entities.enums.UserType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(nullable = false, unique = true)
    private String email;
    private String name;
    private String surname;
    private String acronym;
    @Column(name = "profile_photo")
    private String profilePhoto;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "update_date")
    private Date updateDate;
    private UserType type;
    // rels
    @OneToMany(mappedBy = "user")
    private List<UserSchoolEntity> schools;
    @OneToMany(mappedBy = "user")
    private List<UserWorkEntity> works;
    @OneToMany(mappedBy = "user")
    private List<ArticleEntity> articles;

}
