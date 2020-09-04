package cz.anona.snyverse.entities.mappers;

import cz.anona.snyverse.dtos.UserDTO;
import cz.anona.snyverse.entities.UserEntity;
import org.mapstruct.*;

@Mapper
public interface UserMapper {

    UserDTO toDTO(UserEntity userEntity, @Context CycleAvoidingMappingContext context);
    UserEntity toEntity(UserDTO userDTO, @Context CycleAvoidingMappingContext context);

}
