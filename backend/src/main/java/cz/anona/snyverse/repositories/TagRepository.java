package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.article.Tag;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;


public interface TagRepository extends Neo4jRepository<Tag, Long> {
    List<Tag> findAllByName(String name);
}
