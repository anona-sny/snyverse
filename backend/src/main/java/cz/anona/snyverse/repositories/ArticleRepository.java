package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.ArticleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Long> {
}
