package cz.anona.snyverse.dtos;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class WorkDTO {

    private Long id;
    private String name;
    private OffsetDateTime workFrom;
    private OffsetDateTime workTo;

}
