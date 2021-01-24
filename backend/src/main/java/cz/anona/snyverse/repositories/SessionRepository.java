package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findAllBySession(String session);
    List<SessionEntity> findAllByUserId(Long userId);
}
