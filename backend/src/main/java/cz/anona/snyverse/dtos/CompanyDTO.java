package cz.anona.snyverse.dtos;

import cz.anona.snyverse.entities.enums.CountryCode;
import lombok.Data;

@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private CountryCode country;
}
