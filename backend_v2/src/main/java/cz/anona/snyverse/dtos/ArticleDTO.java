package cz.anona.snyverse.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ArticleDTO {

    private Long id;
    private UserDTO user;
    private CategoryDTO category;
    private String header;
    private String body;
    private OffsetDateTime createdDate;
    private OffsetDateTime updateDate;
    private List<ArticleTagDTO> tags;

}
