package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.ArticleDTO;
import cz.anona.snyverse.entities.ArticleEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses = {TagMapper.class}, componentModel = "spring")
public interface ArticleMapper {

    ArticleDTO toDTO(ArticleEntity articleEntity, @Context CycleAvoidingMappingContext context);
    ArticleEntity toEntity(ArticleDTO articleDTO, @Context CycleAvoidingMappingContext context);

}
