package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.UserSchoolDTO;
import cz.anona.snyverse.entities.UserSchoolEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserSchoolMapper {

    UserSchoolDTO toDTO(UserSchoolEntity entity);
    UserSchoolEntity toEntity(UserSchoolDTO dto);

}
