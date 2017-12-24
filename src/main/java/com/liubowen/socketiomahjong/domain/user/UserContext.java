package com.liubowen.socketiomahjong.domain.user;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.domain.room.Room;
import com.liubowen.socketiomahjong.domain.room.RoomContext;
import com.liubowen.socketiomahjong.domain.room.Seat;
import com.liubowen.socketiomahjong.session.Session;
import com.liubowen.socketiomahjong.session.SessionContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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

    @Autowired
    private RoomContext roomContext;

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

    public void sendMessage(Long userId, String event, Object message) {
        User user = this.get(userId);
        if (user == null) {
            return;
        }
        if (!user.hasSession()) {
            return;
        }
        String sessionId = user.getSessionId();
        this.sessionContext.sendToSession(sessionId, event, message);
    }

    public void sendMessage(Long userId, String event) {
        User user = this.get(userId);
        if (user == null) {
            return;
        }
        if (!user.hasSession()) {
            return;
        }
        String sessionId = user.getSessionId();
        this.sessionContext.sendToSession(sessionId, event);
    }

    public void kickAllInRoom(String roomId) {
        if (StringUtils.isBlank(roomId)) {
            return;
        }
        Room room = this.roomContext.getRoom(roomId);
        if (room == null) {
            return;
        }
        List<Seat> seats = room.allSeat();
        seats.forEach(seat -> {
            if (seat.hasRoomPlayer()) {
                long userId = seat.getUserId();
                User user = this.get(userId);
                if (user.hasSession()) {
                    String sessionId = user.getSessionId();
                    Session session = this.sessionContext.get(sessionId);
                    session.disconnect();
                }
                this.remove(userId);
            }
        });


    }

    public void bind(long userId, SocketIOClient client) {
        Session session = Session.get(client);
        session.login(userId);
        User user = this.get(userId);
        user.bind(session.sessionId());
    }

    public void bind(long userId, Session session) {
        session.login(userId);
        User user = this.get(userId);
        user.bind(session.sessionId());
    }

    public void broacastInRoom(Long senderId, boolean includingSender, String event, Object message) {
        String roomId = this.roomContext.getUserRoomId(senderId);
        if (StringUtils.isBlank(roomId)) {
            return;
        }
        Room room = this.roomContext.getRoom(roomId);
        if (room == null) {
            return;
        }
        List<Seat> seats = room.allSeat();
        for (Seat seat : seats) {
            if (seat.hasRoomPlayer()) {
                long userId = seat.getUserId();
                if (userId == senderId && !includingSender) {
                    continue;
                }
                this.sendMessage(userId, event, message);
            }
        }
    }

}
