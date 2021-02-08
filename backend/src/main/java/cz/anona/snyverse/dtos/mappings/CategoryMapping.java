package cz.anona.snyverse.dtos.mappings;

import cz.anona.snyverse.dtos.CategoryDTO;
import cz.anona.snyverse.entities.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper
public class CategoryMapping {

    public CategoryDTO toDTO(CategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setName(categoryEntity.getName());
        categoryDTO.setParent(categoryEntity.getParentCategory().getName());
        categoryDTO.setChildren(categoryEntity.getChildren().stream().map(CategoryEntity::getName).collect(Collectors.toList()));
        return categoryDTO;
    }
}
