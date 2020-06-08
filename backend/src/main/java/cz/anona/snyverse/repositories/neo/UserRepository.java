package cz.anona.snyverse.repositories.neo;

import cz.anona.snyverse.entities.neo.user.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, Long> {
    List<User> findAllByUsername(String username);
}
