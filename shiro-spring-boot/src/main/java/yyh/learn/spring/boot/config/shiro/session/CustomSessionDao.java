package yyh.learn.spring.boot.config.shiro.session;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 存储session
 * 可以是数据库(mysql,redis,等)，也可以是内存
 * 这里用内存存储
 */
public class CustomSessionDao extends CachingSessionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSessionDao.class);
    private ConcurrentMap<Serializable, Session> sessions = new ConcurrentHashMap();


    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.storeSession(sessionId, session);
        return sessionId;
    }

    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        } else {
            return (Session) this.sessions.putIfAbsent(id, session);
        }
    }

    protected Session doReadSession(Serializable sessionId) {
        return (Session) this.sessions.get(sessionId);
    }

    public void update(Session session) throws UnknownSessionException {
        this.storeSession(session.getId(), session);
    }

    @Override
    protected void doUpdate(Session session) {
        update(session);
    }

    public void delete(Session session) {
        if (session == null) {
            throw new NullPointerException("session argument cannot be null.");
        } else {
            Serializable id = session.getId();
            if (id != null) {
                this.sessions.remove(id);
            }

        }
    }

    @Override
    protected void doDelete(Session session) {
        delete(session);
    }

    public Collection<Session> getActiveSessions() {
        Collection<Session> values = this.sessions.values();
        return (Collection) (CollectionUtils.isEmpty(values) ? Collections.emptySet() : Collections.unmodifiableCollection(values));
    }

}
