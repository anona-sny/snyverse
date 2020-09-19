package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
