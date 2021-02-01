package cz.anona.snyverse.dtos.mappings;

import cz.anona.snyverse.dtos.UserDTO;
import cz.anona.snyverse.entities.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper
public class UserMapping {

    @Autowired
    private CompanyMapping companyMapping;

    public UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setUsername(userEntity.getLoginEntity().getUsername());
        userDTO.setUserType(userEntity.getType());
        userDTO.setCountry(userEntity.getCountry());
        userDTO.setLanguage(userEntity.getLanguage());
        userDTO.setCompanies(userEntity.getCompanyEntities().stream()
                .map(userCompanyEntity -> companyMapping.toDTO(userCompanyEntity.getCompany()))
                .collect(Collectors.toList()));
        return userDTO;
    }
}
