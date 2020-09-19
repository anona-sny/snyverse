package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.TagDTO;
import cz.anona.snyverse.entities.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "author", source = "tagEntity.author.id")
    TagDTO toDTO(TagEntity tagEntity);
    @Mapping(target = "author.id", source = "tagDTO.author")
    TagEntity toEntity(TagDTO tagDTO);

    List<TagEntity> toListEntity(List<TagDTO> tagDTOList);
    List<TagDTO> toListDTO(List<TagEntity> tagEntityList);
}
