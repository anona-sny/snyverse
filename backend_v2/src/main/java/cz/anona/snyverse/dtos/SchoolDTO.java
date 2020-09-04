package cz.anona.snyverse.dtos;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class SchoolDTO {

    private Long id;
    private String name;
    private String url;
    private String city;
    private String country;
    private OffsetDateTime studiedFrom;
    private OffsetDateTime studiedTo;

}
