package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.StoredSession;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface SessionRepository extends Neo4jRepository<StoredSession, Long> {
    List<StoredSession> findAllBySession(String session);
    List<StoredSession> findAllByUser(Long id);
}
