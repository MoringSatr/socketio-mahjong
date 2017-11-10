package com.liubowen.socketiomahjong.domain.user;

import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.session.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

/**
 * @author liubowen
 * @date 2017/11/10 6:02
 * @description
 */
@Component
public class UserContext {

    @Autowired
    private SessionContext sessionContext;

    private ConcurrentMap<Long, User> userMap = Maps.newConcurrentMap();

    public void add(User user) {
        this.userMap.putIfAbsent(user.userId(), user);
    }

    public void remove(Long userId) {
        this.userMap.remove(userId);
    }

    public User get(Long userId) {
        return this.userMap.get(userId);
    }

    public boolean isOnline(Long userId) {
        return this.get(userId) != null;
    }

    public int onlineSize() {
        return this.userMap.size();
    }

    public void sendMessage(Long userId, String event, Object... message) {
        User user = this.get(userId);
        if(user == null) {
            return;
        }
        if(!user.hasSession()) {
            return;
        }
        String sessionId = user.getSessionId();
        this.sessionContext.sendToSession(sessionId, event, message);
    }

    public void kickAllInRoom(Integer roomId) {
        if(roomId == null || roomId == 0) {
            return;
        }

    }
}
