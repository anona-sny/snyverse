package cz.anona.snyverse.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.anona.snyverse.dtos.CompanyDTO;
import cz.anona.snyverse.dtos.FilterColumnDTO;
import cz.anona.snyverse.dtos.PagedSortedFilterDTO;
import cz.anona.snyverse.dtos.StatusDTO;
import cz.anona.snyverse.dtos.mappings.CompanyMapping;
import cz.anona.snyverse.entities.CompanyEntity;
import cz.anona.snyverse.entities.enums.StatusType;
import cz.anona.snyverse.entities.exceptions.AuthorizationException;
import cz.anona.snyverse.entities.exceptions.CompanyException;
import cz.anona.snyverse.services.CompanyService;
import cz.anona.snyverse.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapping companyMapping;
    private final SessionService sessionService;

    @Autowired
    public CompanyController(CompanyService companyService, CompanyMapping cm1, SessionService s1) {
        this.companyService = companyService;
        this.companyMapping = cm1;
        this.sessionService = s1;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Page<CompanyDTO>> listCompanies(@RequestBody PagedSortedFilterDTO pagedSortedFilterDTO) throws JsonProcessingException {
        String name = "";
        for(FilterColumnDTO fcd: pagedSortedFilterDTO.getColumns()) {
            if(fcd.getColumn().equals("name")) {
                name = fcd.getValue();
            }
        }
        Pageable pageable = PageRequest.of(pagedSortedFilterDTO.getPageNumber(), pagedSortedFilterDTO.getPageCount());
        return ResponseEntity.ok(this.companyService.listCompanies(pageable, name).map(companyMapping::toDTO));
    }


    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCompany(@RequestBody CompanyDTO companyDTO) throws CompanyException, AuthorizationException {
        if(this.sessionService.isLogged()) {
            this.companyService.addCompany(companyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(StatusDTO.builder().message("Company was created").result(StatusType.CREATED).build());
        } else {
            throw new AuthorizationException("User cannot add new company without login");
        }
    }

    // put company to account (with from, to)
    // delete company from account


    // exception handler
}
