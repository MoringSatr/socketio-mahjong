package com.liubowen.socketiomahjong.session;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liubowen
 * @date 2017/11/10 1:55
 * @description 玩家连接信息
 */
@ToString
@Setter
public class Session {

    /** 玩家客户端连接 */
    private SocketIOClient client;

    /** 玩家id */
    private String id;

    private Session() {
        this.id = "";
    }

    public static Session get(SocketIOClient client) {
        Session session = new Session();
        session.setClient(client);
        return session;
    }

    public String sessionId() {
        if(client == null) {
            return "";
        }
        return client.getSessionId().toString();
    }

    public String id() {
        return this.id;
    }

    public boolean isLogin() {
        return !"".equals(this.id);
    }

    public void send(String event, Object... message) {
        this.client.sendEvent(event, message);
    }

    public void sendAndClose(String event, Object... message) {
        this.send(event, message);
        this.client.disconnect();
    }

}
