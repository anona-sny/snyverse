package cz.anona.snyverse.controllers.dtos;

import cz.anona.snyverse.entities.enums.StatusType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusDTO {

    StatusType result;
    String message;

}
