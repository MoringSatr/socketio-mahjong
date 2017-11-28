package com.liubowen.socketiomahjong.handler;

import com.liubowen.socketiomahjong.session.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author liubowen
 * @date 2017/11/10 3:48
 * @description 游戏业务处理器
 */
@Component
@Slf4j
public class GameBusinessHandler {

    /**
     * 准备
     */
    public void ready(Session session, Object data) {
    }

    /**
     * 换牌
     */
    public void huanpai(Session session, Object data) {
    }

    /**
     * 定缺
     */
    public void dingque(Session session, Object data) {
    }

    /**
     * 出牌
     */
    public void chupai(Session session, Object data) {
    }

    /**
     * 碰
     */
    public void peng(Session session, Object data) {
    }

    /**
     * 杠
     */
    public void gang(Session session, Object data) {
    }

    /**
     * 胡
     */
    public void hu(Session session, Object data) {
    }

    /**
     * 过 遇上胡，碰，杠的时候，可以选择过
     */
    public void guo(Session session, Object data) {
    }

    /**
     * 聊天
     */
    public void chat(Session session, Object data) {
    }

    /**
     * 快速聊天
     */
    public void quickChat(Session session, Object data) {
    }

    /**
     * 语音聊天
     */
    public void voiceMsg(Session session, Object data) {
    }

    /**
     * 表情
     */
    public void emoji(Session session, Object data) {
    }

    /**
     * 退出房间
     */
    public void exit(Session session, Object data) {
    }

    /**
     * 解散房间
     */
    public void dispress(Session session, Object data) {
    }

    /**
     * 解散房间请求
     */
    public void dissolveRequest(Session session, Object data) {
    }

    /**
     * 同意解散
     */
    public void dissolveAgree(Session session, Object data) {
    }

    /**
     * 拒绝解散
     */
    public void dissolveReject(Session session, Object data) {
    }

}
