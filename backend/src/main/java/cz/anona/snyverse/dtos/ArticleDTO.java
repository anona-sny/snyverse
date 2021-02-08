package cz.anona.snyverse.dtos;

import cz.anona.snyverse.entities.enums.LanguageCode;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    private UserDTO author;
    private OffsetDateTime creationDate;
    private LanguageCode language;
    private String name;
    private String body;
    private CategoryDTO category;
    private List<Long> tags;    // ids
    private List<Long> files;   // ids
}
