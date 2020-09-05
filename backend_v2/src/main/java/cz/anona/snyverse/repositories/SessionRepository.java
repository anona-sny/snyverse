package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findAllBySession(String session);
    List<Session> findAllByUserId(Long id);
}
