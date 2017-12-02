package com.liubowen.socketiomahjong.session;

import com.corundumstudio.socketio.SocketIOClient;
import io.netty.util.AttributeKey;
import lombok.ToString;

/**
 * @author liubowen
 * @date 2017/11/10 1:55
 * @description 玩家连接信息
 */
@ToString
public class Session {

    private static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("SESSION");

    /** 玩家客户端连接 */
    private SocketIOClient client;

    /** 玩家id */
    private long userId;

    private Session() {
    }

    public static Session get(SocketIOClient client) {
        Session session = client.get(SESSION_KEY.name());
        if(session == null) {
            session = new Session();
            session.bind(client);
        }
        return session;
    }

    /** 玩家登陆，绑定玩家id */
    public void login(long userId) {
        this.userId = userId;
    }

    public static void main(String[] args) throws Exception {
    }

    /** 获取玩家sessionId */
    public String sessionId() {
        if(client == null) {
            return "";
        }
        return client.getSessionId().toString();
    }

    /** 玩家id */
    public long userId() {
        return this.userId;
    }

    /** 玩家是否登陆 */
    public boolean isLogin() {
        return this.userId != 0;
    }

    /** 发送消息 */
    public void send(String event, Object... message) {
        this.client.sendEvent(event, message);
    }

    /** 发送消息并且关闭连接 */
    public void sendAndClose(String event, Object... message) {
        this.send(event, message);
        this.client.disconnect();
    }

    /** 绑定玩家session */
    private void bind(SocketIOClient client) {
        this.client = client;
        client.set(SESSION_KEY.name(), this);
    }

}
