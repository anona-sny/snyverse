package cz.anona.snyverse.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ArticleTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

}
