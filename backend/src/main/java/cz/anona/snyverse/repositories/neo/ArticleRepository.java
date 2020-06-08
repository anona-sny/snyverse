package cz.anona.snyverse.repositories.neo;

import cz.anona.snyverse.entities.neo.article.Article;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface ArticleRepository extends Neo4jRepository<Article, Long> {
    List<Article> findAll();
}
