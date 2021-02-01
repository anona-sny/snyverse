package cz.anona.snyverse.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDTO {

    private String type;
    private String message;
    private Object details;

}
