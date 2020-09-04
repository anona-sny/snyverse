package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.WorkDTO;
import cz.anona.snyverse.entities.WorkEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WorkMapper {

    WorkEntity toEntity(WorkDTO workDTO);
    WorkDTO toDTO(WorkEntity workEntity);

}
