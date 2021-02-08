package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.ArticleDataEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDataRepository extends PagingAndSortingRepository<ArticleDataEntity, Long> {

    List<ArticleDataEntity> findByNameEquals(String name);

}
