package cz.anona.snyverse.repositories.jpa;

import cz.anona.snyverse.entities.jpa.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findAllBySession(String session);
    List<Session> findAllByUser(Long id);
}
