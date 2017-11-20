package com.liubowen.socketiomahjong.domain.user;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.session.Session;
import com.liubowen.socketiomahjong.session.SessionContext;
import lombok.experimental.var;
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

    public void bind(long userId, SocketIOClient client) {
        Session session = Session.get(client);
        session.login(userId);
    }

//    exports.bind = function(userId,socket){
//        userList[userId] = socket;
//        userOnline++;
//    };
//
//    exports.del = function(userId,socket){
//        delete userList[userId];
//        userOnline--;
//    };
//
//    exports.get = function(userId){
//        return userList[userId];
//    };
//
//    exports.isOnline = function(userId){
//        var data = userList[userId];
//        if(data != null){
//            return true;
//        }
//        return false;
//    };
//
//    exports.getOnlineCount = function(){
//        return userOnline;
//    }
//
//    exports.sendMsg = function(userId,event,msgdata){
//        console.log(event);
//        var userInfo = userList[userId];
//        if(userInfo == null){
//            return;
//        }
//        var socket = userInfo;
//        if(socket == null){
//            return;
//        }
//
//        socket.emit(event,msgdata);
//    };
//
//    exports.kickAllInRoom = function(roomId){
//        if(roomId == null){
//            return;
//        }
//        var roomInfo = roomMgr.getRoom(roomId);
//        if(roomInfo == null){
//            return;
//        }
//
//        for(var i = 0; i < roomInfo.seats.length; ++i){
//            var rs = roomInfo.seats[i];
//
//            //如果不需要发给发送方，则跳过
//            if(rs.userId > 0){
//                var socket = userList[rs.userId];
//                if(socket != null){
//                    exports.del(rs.userId);
//                    socket.disconnect();
//                }
//            }
//        }
//    };
//
//    exports.broacastInRoom = function(event,data,sender,includingSender){
//        var roomId = roomMgr.getUserRoom(sender);
//        if(roomId == null){
//            return;
//        }
//        var roomInfo = roomMgr.getRoom(roomId);
//        if(roomInfo == null){
//            return;
//        }
//
//        for(var i = 0; i < roomInfo.seats.length; ++i){
//            var rs = roomInfo.seats[i];
//
//            //如果不需要发给发送方，则跳过
//            if(rs.userId == sender && includingSender != true){
//                continue;
//            }
//            var socket = userList[rs.userId];
//            if(socket != null){
//                socket.emit(event,data);
//            }
//        }
}
