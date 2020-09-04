package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.SchoolDTO;
import cz.anona.snyverse.entities.SchoolEntity;
import org.mapstruct.Mapper;

@Mapper
public interface SchoolMapper {

    SchoolDTO toDTO(SchoolEntity schoolEntity);
    SchoolEntity toEntity(SchoolDTO schoolDTO);

}
