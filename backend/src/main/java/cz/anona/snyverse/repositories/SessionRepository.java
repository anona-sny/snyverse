package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findAllBySession(String session);
    List<Session> findAllByUserId(Long id);
}
