package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.UserSchoolDTO;
import cz.anona.snyverse.entities.UserSchoolEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {TagMapper.class}, componentModel = "spring")
public interface UserSchoolMapper {

    UserSchoolDTO toDTO(UserSchoolEntity entity);
    UserSchoolEntity toEntity(UserSchoolDTO dto);

}
