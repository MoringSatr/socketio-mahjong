package com.liubowen.socketiomahjong.session;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @author liubowen
 * @date 2017/11/10 1:55
 * @description
 */
@Component
public class SessionContext {

    private ConcurrentMap<String, Session> sessionConcurrentMap = Maps.newConcurrentMap();

    public void add(Session session) {
        this.sessionConcurrentMap.putIfAbsent(session.sessionId(), session);
    }

    public Session get(String sessionId) {
        return this.sessionConcurrentMap.get(sessionId);
    }

    public void remove(String sessionId) {
        this.sessionConcurrentMap.remove(sessionId);
    }

    public List<Session> all() {
        return Lists.newArrayList(this.sessionConcurrentMap.values());
    }

    public void sendToAll(String event, Object message) {
        this.all().forEach(session -> {
            session.send(event, message);
        });
    }

    public void sendToSession(String sessionId, String event, Object message) {
        if(this.sessionConcurrentMap.containsKey(sessionId)) {
            this.get(sessionId).send(event, message);
        }
    }

    public Session get(SocketIOClient client) {
        String sessionId = client.getSessionId().toString();
        return this.get(sessionId);
    }
    
}
