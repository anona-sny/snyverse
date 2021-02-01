package cz.anona.snyverse.services;

import cz.anona.snyverse.dtos.CompanyDTO;
import cz.anona.snyverse.entities.CompanyEntity;
import cz.anona.snyverse.entities.enums.CompanyExceptionType;
import cz.anona.snyverse.entities.exceptions.CompanyException;
import cz.anona.snyverse.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void addCompany(CompanyEntity companyEntity) {
        this.companyRepository.save(companyEntity);
    }

    public boolean companyExist(String name) {
        return this.companyRepository.findAllByNameEquals(name).size()>0;
    }

    public Page<CompanyEntity> listCompanies(Pageable pageable, String nameLike) {
        return this.companyRepository.findAllByNameContains(pageable, nameLike);
    }

    public void addCompany(CompanyDTO companyDTO) throws CompanyException {
        if(companyDTO == null) {
            throw new CompanyException(CompanyExceptionType.OTHER, "Object null");
        } else if(companyDTO.getName() == null || companyDTO.getName().equals("")) {
            throw new CompanyException(CompanyExceptionType.NAME_INVALID, "Name is empty");
        } else if(this.companyRepository.findAllByNameEquals(companyDTO.getName()).size() > 0) {
            throw new CompanyException(CompanyExceptionType.NAME_OCCUPIED, "Name is occupied");
        } else {
            // create object
            CompanyEntity companyEntity = CompanyEntity.of(companyDTO.getName(), companyDTO.getCountry());
            this.companyRepository.save(companyEntity);
        }
    }

}
