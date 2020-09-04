package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.TagDTO;
import cz.anona.snyverse.entities.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper
public interface TagMapper {

    @Mapping(target = "author", ignore = true)
    TagDTO toDTO(TagEntity tagEntity);
    TagEntity toEntity(TagDTO tagDTO);

}
