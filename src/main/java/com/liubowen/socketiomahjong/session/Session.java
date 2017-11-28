package com.liubowen.socketiomahjong.session;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.ToString;

/**
 * @author liubowen
 * @date 2017/11/10 1:55
 * @description 玩家连接信息
 */
@ToString
public class Session {

    private static final String sessionKey = "SESSION";

    /** 玩家客户端连接 */
    private SocketIOClient client;

    /** 玩家id */
    private long userId;

    private Session() {
    }

    public static Session get(SocketIOClient client) {
        Session session = client.get(sessionKey);
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

    /** 绑定玩家session */
    private void bind(SocketIOClient client) {
        this.client = client;
        client.set(sessionKey, this);
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

}
