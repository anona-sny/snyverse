package cz.anona.snyverse.repositories.neo;

import cz.anona.snyverse.entities.neo.user.UserHistory;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface UserHistoryRepository extends Neo4jRepository<UserHistory, Long> {
    List<UserHistory> findAllByUsername(String username);
}
