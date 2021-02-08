package cz.anona.snyverse.controllers;

import cz.anona.snyverse.dtos.CategoryDTO;
import cz.anona.snyverse.dtos.FilterColumnDTO;
import cz.anona.snyverse.dtos.PagedSortedFilterDTO;
import cz.anona.snyverse.dtos.mappings.CategoryMapping;
import cz.anona.snyverse.entities.CategoryEntity;
import cz.anona.snyverse.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private CategoryService categoryService;

    private CategoryMapping categoryMapping;

    @Autowired
    public CategoryController(CategoryService cs, CategoryMapping cm) {
        this.categoryService = cs;
        this.categoryMapping = cm;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Page<CategoryDTO>> listCategories(@RequestBody PagedSortedFilterDTO pagedSortedFilterDTO) {
        String name = "";
        for(FilterColumnDTO fcd: pagedSortedFilterDTO.getColumns()) {
            if(fcd.getColumn().equals("name")) {
                name = fcd.getValue();
            }
        }
        Pageable pageable = PageRequest.of(pagedSortedFilterDTO.getPageNumber(), pagedSortedFilterDTO.getPageCount());
        return ResponseEntity.ok(this.categoryService.listCategories(pageable, name).map(categoryMapping::toDTO));
    }

}
