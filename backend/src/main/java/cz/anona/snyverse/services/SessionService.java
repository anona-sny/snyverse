package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.StoredSession;
import cz.anona.snyverse.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private HttpSession httpSession;

    private final int sessionTTL = 1440; // in seconds

    public StoredSession getSession() {
        List<StoredSession> sessions = this.sessionRepository.findAllBySession(httpSession.getId());
        if(sessions.size() > 0) {
            return sessions.get(0);
        } else {
            return null;
        }
    }

    public void associateSession(Long id) {
        List<StoredSession> sessions = this.sessionRepository.findAllBySession(httpSession.getId());
        List<StoredSession> sessions2 = this.sessionRepository.findAllByUser(id);
        if(sessions.size() > 0) {
            sessions.forEach(storedSession -> {
                this.sessionRepository.delete(storedSession);
            });
        }
        if(sessions2.size() > 0) {
            sessions2.forEach(storedSession -> {
                this.sessionRepository.delete(storedSession);
            });
        }
        StoredSession storedSession = new StoredSession();
        storedSession.setSession(this.httpSession.getId());
        storedSession.setUser(id);
        storedSession.setLastAccess(OffsetDateTime.now());
        this.sessionRepository.save(storedSession);
    }

    public boolean isLogged() {
        StoredSession session = this.getSession();

        boolean valid = (session!=null&&session.getUser()!=null);
        valid = valid && (OffsetDateTime.now().toEpochSecond()-session.getLastAccess().toEpochSecond())<this.sessionTTL;
        if(valid) {
            session.setLastAccess(OffsetDateTime.now());
            this.sessionRepository.save(session);
        }
        if(session != null && !valid) {
            this.sessionRepository.delete(session);
        }
        return valid;
    }

}
