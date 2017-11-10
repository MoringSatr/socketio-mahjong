package com.liubowen.socketiomahjong.dispatcher;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.liubowen.socketiomahjong.handle.GameHandle;
import com.liubowen.socketiomahjong.handle.LoginHandle;
import com.liubowen.socketiomahjong.session.Session;
import com.liubowen.socketiomahjong.session.SessionContext;
import com.liubowen.socketiomahjong.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liubowen
 * @date 2017/11/10 2:45
 * @description
 */
@Component
@Slf4j
public class SocketIoMessageDispatcher {

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private LoginHandle loginHandle;

    @Autowired
    private GameHandle gameHandle;

    /** 连接 */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String sessionId = client.getSessionId().toString();
        Session session = Session.get(client);
        this.sessionContext.add(session);
        log.info("【一个玩家加入连接】sessionId : {}", sessionId);
    }

    /** 断开链接  */
    @OnDisconnect
    public void OnDisconnect(SocketIOClient client) {
        Session session = this.sessionContext.get(client);
        loginHandle.disconnect(session);
        this.sessionContext.remove(session.sessionId());
        log.info("【一个玩家断开连接】sessionId : {}", session.sessionId());
    }

    /** 登陆 */
    @OnEvent("login")
    public void login(SocketIOClient client, AckRequest request, LoginVo loginVo) {
        Session session = this.sessionContext.get(client);
        this.loginHandle.login(session, loginVo);
    }

    /** 准备 */
    @OnEvent("ready")
    public void ready(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.ready(session, data);
    }

    /** 换牌 */
    @OnEvent("huanpai")
    public void huanpai(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.huanpai(session, data);
    }

    /** 定缺 */
    @OnEvent("dingque")
    public void dingque(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.dingque(session, data);
    }

    /** 出牌 */
    @OnEvent("chupai")
    public void chupai(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.chupai(session, data);
    }

    /** 碰 */
    @OnEvent("peng")
    public void peng(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.peng(session, data);
    }

    /** 杠 */
    @OnEvent("gang")
    public void gang(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.gang(session, data);
    }

    /** 胡 */
    @OnEvent("hu")
    public void hu(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.hu(session, data);
    }

    /** 过  遇上胡，碰，杠的时候，可以选择过 */
    @OnEvent("guo")
    public void guo(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.guo(session, data);
    }

    /** 聊天 */
    @OnEvent("chat")
    public void chat(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.chat(session, data);
    }

    /** 快速聊天 */
    @OnEvent("quick_chat")
    public void quickChat(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.quickChat(session, data);
    }

    /** 语音聊天 */
    @OnEvent("voice_msg")
    public void voiceMsg(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.voiceMsg(session, data);
    }

    /** 表情 */
    @OnEvent("emoji")
    public void emoji(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.emoji(session, data);
    }

    /** 退出房间 */
    @OnEvent("exit")
    public void exit(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.exit(session, data);
    }

    /** 解散房间 */
    @OnEvent("dispress")
    public void dispress(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.dispress(session, data);
    }

    /** 解散房间请求 */
    @OnEvent("dissolve_request")
    public void dissolveRequest(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.dissolveRequest(session, data);
    }

    /** 同意解散 */
    @OnEvent("dissolve_agree")
    public void dissolveAgree(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.dissolveAgree(session, data);
    }

    /** 拒绝解散 */
    @OnEvent("dissolve_reject")
    public void dissolveReject(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.gameHandle.dissolveReject(session, data);
    }

    /** ping */
    @OnEvent("game_ping")
    public void gamePing(SocketIOClient client, AckRequest request, Object data) {
        Session session = this.sessionContext.get(client);
        this.loginHandle.gamePing(session, data);
    }

}
