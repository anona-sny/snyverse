package cz.anona.snyverse.dtos.mappings;

import cz.anona.snyverse.dtos.CompanyDTO;
import cz.anona.snyverse.entities.CompanyEntity;
import org.mapstruct.Mapper;

@Mapper
public class CompanyMapping {

    public CompanyDTO toDTO(CompanyEntity entity) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(entity.getId());
        companyDTO.setCountry(entity.getCountry());
        companyDTO.setName(entity.getName());
        return companyDTO;
    }

}
