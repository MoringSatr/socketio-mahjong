package com.liubowen.socketiomahjong.handler;

import com.liubowen.socketiomahjong.domain.game.GameManager;
import com.liubowen.socketiomahjong.domain.room.Room;
import com.liubowen.socketiomahjong.domain.room.RoomContext;
import com.liubowen.socketiomahjong.domain.user.UserContext;
import com.liubowen.socketiomahjong.session.Session;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/10 3:48
 * @description 游戏业务处理器
 */
@Component
@Slf4j
public class GameBusinessHandler {

    @Autowired
    private RoomContext roomContext;

    @Autowired
    private UserContext userContext;

    /**
     * 准备
     */
    public void ready(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();

        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();

        gameManager.setReady(userId);
        JSONObject resultData = new JSONObject();
        resultData.put("userid", userId);
        resultData.put("ready", true);

        this.userContext.broacastInRoom(userId, true, "user_ready_push", resultData);
    }

    /**
     * 换牌
     */
    public void huanpai(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        if (data == null) {
            return;
        }
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();
        JSONObject jsonData = JSONObject.fromObject(data);
        String p1 = jsonData.getString("p1");
        String p2 = jsonData.getString("p2");
        String p3 = jsonData.getString("p3");
        if (p1 == null || p2 == null || p3 == null) {
            return;
        }
        gameManager.huanSanZhang(userId, p1, p2, p3);
    }

    /**
     * 定缺
     */
    public void dingque(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();

        String que = (String) data;
        gameManager.dingQue(userId, que);
    }

    /**
     * 出牌
     */
    public void chupai(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();

        String pai = (String) data;
        gameManager.chuPai(userId, pai);
    }

    /**
     * 碰
     */
    public void peng(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();
        gameManager.peng(userId);
    }

    /**
     * 杠
     */
    public void gang(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        if (data == null) {
            return;
        }
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();
        String pai = (String) data;
        gameManager.gang(userId, pai);
    }

    /**
     * 胡
     */
    public void hu(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();
        gameManager.hu(userId);
    }

    /**
     * 过 遇上胡，碰，杠的时候，可以选择过
     */
    public void guo(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();
        gameManager.guo(userId);
    }

    /**
     * 聊天
     */
    public void chat(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        JSONObject result = new JSONObject();
        result.put("sender", userId);
        result.put("content", data);
        // 通知其它客户端
        this.userContext.broacastInRoom(userId, true, "chat_push", result);
    }

    /**
     * 快速聊天
     */
    public void quickChat(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        JSONObject result = new JSONObject();
        result.put("sender", userId);
        result.put("content", data);
        // 通知其它客户端
        this.userContext.broacastInRoom(userId, true, "quick_chat_push", result);
    }

    /**
     * 语音聊天
     */
    public void voiceMsg(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        JSONObject result = new JSONObject();
        result.put("sender", userId);
        result.put("content", data);
        // 通知其它客户端
        this.userContext.broacastInRoom(userId, true, "voice_msg_push", result);

    }

    /**
     * 表情
     */
    public void emoji(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        JSONObject result = new JSONObject();
        result.put("sender", userId);
        result.put("content", data);
        // 通知其它客户端
        this.userContext.broacastInRoom(userId, true, "emoji_push", result);
    }

    /**
     * 退出房间
     */
    public void exit(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();

        // 如果游戏已经开始，则不可以
        if (gameManager.hasBegan()) {
            return;
        }

        // 如果是房主，则只能走解散房间
        if (userRoom.isCreator(userId)) {
            return;
        }
        JSONObject result = new JSONObject();
        result.put("userid", userId);
        // 通知其它玩家，有人退出了房间
        this.userContext.broacastInRoom(userId, false, "exit_notify_push", result);

        this.roomContext.exitRoom(userId);
        this.userContext.remove(userId);

        session.sendAndClose("exit_result");
    }

    /**
     * 解散房间
     */
    public void dispress(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }

        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();

        // 如果游戏已经开始，则不可以
        if (gameManager.hasBegan()) {
            return;
        }

        // 如果不是房主，则不能解散房间
        if (!userRoom.isCreator(userId)) {
            return;
        }
        // 通知其它玩家，有人退出了房间
        this.userContext.broacastInRoom(userId, true, "dispress_push", new JSONObject());
        String roomId = userRoom.getId();
        this.userContext.kickAllInRoom(roomId);
        this.roomContext.destroy(roomId);
        session.disconnect();
    }

    /**
     * 解散房间请求
     */
    public void dissolveRequest(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();

        // 如果游戏未开始，则不可以
        if (!gameManager.hasBegan()) {
            return;
        }

        JSONObject jsonObject = gameManager.dissolveRequest(userId);

        if (jsonObject != null) {
            // var dr = ret.dr;
            // var ramaingTime = (dr.endTime - Date.now()) / 1000;
            // var data = {
            // time:ramaingTime,
            // states:dr.states
            // }
            JSONObject result = new JSONObject();
            result.put("time", userId);
            result.put("states", userId);
            this.userContext.broacastInRoom(userId, true, "dissolve_notice_push", result);
        }

    }

    /**
     * 同意解散
     */
    public void dissolveAgree(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();
        JSONObject jsonObject = gameManager.dissolveAgree(userId, true);
        if (jsonObject == null) {
            return;
        }
        if (jsonObject == null) {
            return;
        }
        List<Boolean> states = jsonObject.getJSONArray("");
        JSONObject result = new JSONObject();
        result.put("time", userId);
        result.put("states", states);
        this.userContext.broacastInRoom(userId, true, "dissolve_notice_push", result);

        boolean doAllAgree = true;
        for (int i = 0; i < states.size(); ++i) {
            if (!states.get(i)) {
                doAllAgree = false;
                break;
            }
        }

        if (doAllAgree) {
            gameManager.doDissolve();
        }

    }

    /**
     * 拒绝解散
     */
    public void dissolveReject(Session session, Object data) {
        if (!session.isLogin()) {
            // 未登陆过的就忽略
            return;
        }
        long userId = session.userId();
        Room userRoom = this.roomContext.getRoomByUserId(userId);
        if (userRoom == null) {
            return;
        }
        GameManager gameManager = userRoom.getGameManager();
        JSONObject jsonObject = gameManager.dissolveAgree(userId, false);
        if (jsonObject == null) {
            return;
        }
        this.userContext.broacastInRoom(userId, true, "dissolve_cancel_push", new JSONObject());

    }

}
