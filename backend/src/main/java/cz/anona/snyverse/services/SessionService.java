package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.jpa.Session;
import cz.anona.snyverse.repositories.jpa.SessionRepository;
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
    protected SessionRepository sessionRepository;

    @Autowired
    protected HttpSession httpSession;

    protected final int sessionTTL = 1440; // in seconds

    public Session getSession() {
        List<Session> sessions = this.sessionRepository.findAllBySession(httpSession.getId());
        if(sessions.size() > 0) {
            return sessions.get(0);
        } else {
            return null;
        }
    }

    public void associateSession(Long id) {
        List<Session> sessions = this.sessionRepository.findAllBySession(httpSession.getId());
        List<Session> sessions2 = this.sessionRepository.findAllByUser(id);
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
        Session storedSession = new Session();
        storedSession.setSession(this.httpSession.getId());
        storedSession.setUser(id);
        storedSession.setLastAccess(OffsetDateTime.now());
        this.sessionRepository.save(storedSession);
    }

    public boolean isLogged() {
        Session session = this.getSession();

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
