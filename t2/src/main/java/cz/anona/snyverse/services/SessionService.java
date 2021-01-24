package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.Session;
import cz.anona.snyverse.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SessionService {

    protected final SessionRepository sessionRepository;

    protected final HttpSession httpSession;

    protected final int sessionTTL = 1440; // in seconds

    @Autowired
    public SessionService(SessionRepository sessionRepository, HttpSession httpSession) {
        this.sessionRepository = sessionRepository;
        this.httpSession = httpSession;
    }

    public Session getSession() {
        List<Session> sessions = this.sessionRepository.findAllBySession(httpSession.getId());
        if(sessions.size() > 0) {
            return sessions.get(0);
        } else {
            return null;
        }
    }

    public void associateSession(Long id) {
        this.dissociateSession(id);
        Session storedSession = new Session();
        storedSession.setSession(this.httpSession.getId());
        storedSession.setUserId(id);
        storedSession.setLastAccess(Date.from(Instant.now()));
        this.sessionRepository.save(storedSession);
    }

    public void dissociateSession(Long id) {
        List<Session> sessions = this.sessionRepository.findAllBySession(httpSession.getId());
        List<Session> sessions2 = this.sessionRepository.findAllByUserId(id);
        if(sessions.size() > 0) {
            sessions.forEach(this.sessionRepository::delete);
        }
        if(sessions2.size() > 0) {
            sessions2.forEach(this.sessionRepository::delete);
        }
    }

    public boolean isLogged() {
        Session session = this.getSession();

        boolean valid = (session!=null&&session.getUserId()!=null);
        valid = valid && (Instant.now().getEpochSecond() -
                session.getLastAccess().toInstant().getEpochSecond()) < this.sessionTTL;
        if(valid) {
            session.setLastAccess(Date.from(Instant.now()));
            this.sessionRepository.save(session);
        }
        if(session != null && !valid) {
            this.sessionRepository.delete(session);
        }
        return valid;
    }

}
