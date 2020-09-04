package cz.anona.snyverse.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

}
