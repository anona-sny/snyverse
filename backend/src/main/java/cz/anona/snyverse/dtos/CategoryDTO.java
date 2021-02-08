package cz.anona.snyverse.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {

    private Long id;
    private String name;
    private String parent;
    private List<String> children;
}
