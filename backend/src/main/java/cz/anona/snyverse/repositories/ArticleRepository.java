package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.article.Article;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ArticleRepository extends Neo4jRepository<Article, Long> {
}
