package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.UserWorkDTO;
import cz.anona.snyverse.entities.UserWorkEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserWorkMapper {

    UserWorkDTO toDTO(UserWorkEntity entity);
    UserWorkEntity toEntity(UserWorkDTO dto);

}
