package cz.anona.snyverse.dtos;

import cz.anona.snyverse.entities.enums.FilterType;
import lombok.Data;

@Data
public class FilterColumnDTO {
    String column;
    String value;
    FilterType type;
}
