package cz.anona.snyverse.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.anona.snyverse.entities.CompanyEntity;
import cz.anona.snyverse.services.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@Slf4j
public class CompanyUnitTest {

    @Autowired
    private CompanyService companyService;

    @Test
    public void testCompanyPaging() throws JsonProcessingException {
        Pageable test = PageRequest.of(0, 2);
        Page<CompanyEntity> testList = this.companyService.listCompanies(test, "o");
        log.warn(new ObjectMapper().writeValueAsString(testList));
        log.warn(new ObjectMapper().writeValueAsString(test));
        Assertions.assertEquals(2, testList.getContent().size());
    }
}
