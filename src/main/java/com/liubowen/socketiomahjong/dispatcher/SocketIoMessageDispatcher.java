package com.liubowen.socketiomahjong.dispatcher;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.liubowen.socketiomahjong.handler.GameBusinessHandler;
import com.liubowen.socketiomahjong.handler.LoginBusinessHandler;
import com.liubowen.socketiomahjong.session.Session;
import com.liubowen.socketiomahjong.session.SessionContext;
import com.liubowen.socketiomahjong.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liubowen
 * @date 2017/11/10 2:45
 * @description 玩家socketio消息分发器
 */
@Component
@Slf4j
public class SocketIoMessageDispatcher {

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private LoginBusinessHandler loginBusinessHandler;

    @Autowired
    private GameBusinessHandler gameBusinessHandler;

    /** 连接 */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        Session session = Session.get(client);
        this.sessionContext.add(session);
        log.info("【一个玩家加入连接】sessionId : {}", session.sessionId());
    }

    /** 断开链接 */
    @OnDisconnect
    public void OnDisconnect(SocketIOClient client) {
        Session session = Session.get(client);
        loginBusinessHandler.disconnect(session);
        this.sessionContext.remove(session.sessionId());
        log.info("【一个玩家断开连接】sessionId : {}", session.sessionId());
    }

    /** 登陆 */
    @OnEvent("login")
    public void login(SocketIOClient client, AckRequest request, LoginVo data) {
        Session session = Session.get(client);
        log.info("【一个玩家登陆】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.loginBusinessHandler.login(session, data);
    }

    /** 准备 */
    @OnEvent("ready")
    public void ready(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家准备】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.ready(session, data);
    }

    /** 换牌 */
    @OnEvent("huanpai")
    public void huanpai(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家换牌】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.huanpai(session, data);
    }

    /** 定缺 */
    @OnEvent("dingque")
    public void dingque(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家定缺】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.dingque(session, data);
    }

    /** 出牌 */
    @OnEvent("chupai")
    public void chupai(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家出牌】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.chupai(session, data);
    }

    /** 碰 */
    @OnEvent("peng")
    public void peng(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家碰】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.peng(session, data);
    }

    /** 杠 */
    @OnEvent("gang")
    public void gang(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家杠】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.gang(session, data);
    }

    /** 胡 */
    @OnEvent("hu")
    public void hu(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家胡】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.hu(session, data);
    }

    /** 过 遇上胡，碰，杠的时候，可以选择过 */
    @OnEvent("guo")
    public void guo(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家过  遇上胡，碰，杠的时候，可以选择过】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.guo(session, data);
    }

    /** 聊天 */
    @OnEvent("chat")
    public void chat(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家聊天】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.chat(session, data);
    }

    /** 快速聊天 */
    @OnEvent("quick_chat")
    public void quickChat(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家快速聊天】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.quickChat(session, data);
    }

    /** 语音聊天 */
    @OnEvent("voice_msg")
    public void voiceMsg(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家语音聊天】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.voiceMsg(session, data);
    }

    /** 表情 */
    @OnEvent("emoji")
    public void emoji(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家表情】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.emoji(session, data);
    }

    /** 退出房间 */
    @OnEvent("exit")
    public void exit(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家退出房间】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.exit(session, data);
    }

    /** 解散房间 */
    @OnEvent("dispress")
    public void dispress(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家解散房间】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.dispress(session, data);
    }

    /** 解散房间请求 */
    @OnEvent("dissolve_request")
    public void dissolveRequest(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家解散房间请求】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.dissolveRequest(session, data);
    }

    /** 同意解散 */
    @OnEvent("dissolve_agree")
    public void dissolveAgree(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家同意解散】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.dissolveAgree(session, data);
    }

    /** 拒绝解散 */
    @OnEvent("dissolve_reject")
    public void dissolveReject(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家拒绝解散】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.gameBusinessHandler.dissolveReject(session, data);
    }

    /** ping */
    @OnEvent("game_ping")
    public void gamePing(SocketIOClient client, AckRequest request, Object data) {
        Session session = Session.get(client);
        log.info("【一个玩家ping】sessionId : {}， userId : {} , data : {}", session.sessionId(), session.userId(), data);
        this.loginBusinessHandler.gamePing(session, data);
    }

}
