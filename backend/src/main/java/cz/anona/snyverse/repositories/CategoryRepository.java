package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {

    Page<CategoryEntity> findAllByNameContains(Pageable pageable, String name);

}
