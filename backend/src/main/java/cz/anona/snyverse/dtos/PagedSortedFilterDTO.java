package cz.anona.snyverse.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PagedSortedFilterDTO {

    private List<FilterColumnDTO> columns;
    private Integer pageNumber;
    private Integer pageCount;
    private String sortBy;

}
