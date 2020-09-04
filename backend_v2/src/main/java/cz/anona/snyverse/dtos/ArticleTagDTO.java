package cz.anona.snyverse.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class ArticleTagDTO {

    private Long id;
    @JsonBackReference
    private ArticleDTO article;
    private TagDTO tag;

}
