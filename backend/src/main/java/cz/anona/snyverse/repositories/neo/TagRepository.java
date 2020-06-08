package cz.anona.snyverse.repositories.neo;

import cz.anona.snyverse.entities.neo.article.Tag;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;


public interface TagRepository extends Neo4jRepository<Tag, Long> {
    List<Tag> findAllByName(String name);
    List<Tag> findAll();

    @Query("MATCH (tag:Tag) WHERE tag.name CONTAINS '' RETURN tag")
    List<Tag> findAllWithNameLike(String name);
}
