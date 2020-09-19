package cz.anona.snyverse.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryEntity category;
    private String header;
    @Column(name="body", columnDefinition="JSON")
    private String body;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "update_date")
    private Date updateDate;
    @OneToMany(mappedBy = "article")
    private List<ArticleTagEntity> tags;

}
