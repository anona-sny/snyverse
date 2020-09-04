package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.CategoryDTO;
import cz.anona.snyverse.entities.CategoryEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;


@Mapper
public interface CategoryMapper {

    CategoryDTO toDTO(CategoryEntity categoryEntity, @Context CycleAvoidingMappingContext context);
    CategoryEntity toEntity(CategoryDTO categoryDTO, @Context CycleAvoidingMappingContext context);

}
