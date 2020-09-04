package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.ArticleTagDTO;
import cz.anona.snyverse.entities.ArticleTagEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ArticleTagMapper {

    ArticleTagDTO toDTO(ArticleTagEntity entity);
    ArticleTagEntity toEntity(ArticleTagDTO dto);

}
