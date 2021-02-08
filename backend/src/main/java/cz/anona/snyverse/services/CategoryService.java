package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.CategoryEntity;
import cz.anona.snyverse.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository cs) {
        this.categoryRepository = cs;
    }

    public Page<CategoryEntity> listCategories(Pageable pageable, String nameLike) {
        return this.categoryRepository.findAllByNameContains(pageable, nameLike);
    }

    public CategoryEntity getCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }
}
