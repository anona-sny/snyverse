package cz.anona.snyverse.entities.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FrontendExceptionObject {

    private String type;
    private String message;
    private Object details;

}
