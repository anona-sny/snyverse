package cz.anona.snyverse.dtos;

import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;
    private CategoryDTO subcategoryOf;
    private String name;

}
