package com.liubowen.socketiomahjong.handler;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.domain.room.RoomContext;
import com.liubowen.socketiomahjong.domain.user.TokenContext;
import com.liubowen.socketiomahjong.domain.user.User;
import com.liubowen.socketiomahjong.domain.user.UserContext;
import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.session.Session;
import com.liubowen.socketiomahjong.test.TestRoom;
import com.liubowen.socketiomahjong.test.TestRoomContext;
import com.liubowen.socketiomahjong.test.TestRoomSeatData;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import com.liubowen.socketiomahjong.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liubowen
 * @date 2017/11/10 1:58
 * @description
 */
@Component
@Slf4j
public class LoginBusinessHandler {

    @Autowired
    private TokenContext tokenContext;

    @Autowired
    private RoomContext roomContext;

    @Autowired
    private UserContext userContext;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private TestRoomContext testRoomContext;

    /** 登陆 */
    public void login(Session session, LoginVo loginVo) {
        if (session.isLogin()) {
            // 已经登陆过的就忽略
            return;
        }
        // 检查参数合法性
        if (!loginVo.isLegal()) {
            session.send("login_result", ResultEntityUtil.err(1, "invalid parameters").result());
            return;
        }
        // 检查参数是否被篡改
        if (!loginVo.isMd5()) {
            session.send("login_result", ResultEntityUtil.err(2, "login failed. invalid sign!").result());
            return;
        }

        String token = loginVo.getToken();
        // 检查token是否有效
        if (!this.tokenContext.isTokenValid(token)) {
            session.send("login_result", ResultEntityUtil.err(3, "token out of time.").result());
            return;
        }

        // 检查房间合法性
        long userId = this.tokenContext.getUserId(token);
        String roomId = this.roomContext.getUserRoomId(userId);
        UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(userId);
        User user = new User(userInfo);
        this.userContext.add(user);
        this.userContext.bind(userId, session);

        TestRoom room = this.testRoomContext.getTestRoom();



        JSONObject userData = null;
        JSONArray seats = new JSONArray();
        for (TestRoomSeatData testRoomSeatData : room.allTestRoomSeatDatas()) {
            boolean online = false;
            int score = 0;
            String name = "";
            if (testRoomSeatData.hasPlayer()) {
                online = userContext.isOnline(testRoomSeatData.getPlayerId());
                score = testRoomSeatData.getScore();
                name = testRoomSeatData.getNickname();
            }
            JSONObject seatJson = new JSONObject();
            seatJson.put("userid", testRoomSeatData.getPlayerId());
            seatJson.put("ip", testRoomSeatData.getIp());
            seatJson.put("score", score);
            seatJson.put("name", name);
            seatJson.put("online", online);
            seatJson.put("ready", testRoomSeatData.isReady());
            seatJson.put("seatindex", testRoomSeatData.getIndex());
            seats.add(seatJson);

            if (userId == testRoomSeatData.getPlayerId()) {
                userData = seatJson;
            }
        }
        // 通知前端
        ResultEntity resultEntity = ResultEntityUtil.ok();
        resultEntity.add("roomid", room.getId());
        resultEntity.add("conf", room.getConf());
        resultEntity.add("numofgames", room.getNumOfGames());
        resultEntity.add("seats", seats);

        session.send("login_result", resultEntity.result());

        // 通知其它客户端
        this.userContext.broacastInRoom(userId, false, "new_user_comes_push", userData);

        // 玩家上线，强制设置为TRUE
//        this.roomContext.setReady(userId, true);
        room.setReady(userId, true);

        session.send("login_finished");


        // 返回房间信息
//        Room roomInfo = this.roomContext.getRoom(roomId);

        // int seatIndex = this.roomContext.getUserSeat(userId);

//        Seat seat = roomInfo.getSeatByUserId(userId);
//        seat.setIp(session.ip());
//
//        JSONObject userData = null;
//        JSONArray seats = new JSONArray();
//        for (Seat seat1 : roomInfo.allSeat()) {
//            boolean online = false;
//            int score = 0;
//            String name = "";
//            if (seat1.hasRoomPlayer()) {
//                online = userContext.isOnline(seat1.getUserId());
//                score = seat1.getRoomPlayerInfo().getScore();
//                name = seat1.getRoomPlayerInfo().getName();
//            }
//            JSONObject seatJson = new JSONObject();
//            seatJson.put("userid", seat1.getUserId());
//            seatJson.put("ip", seat1.getIp());
//            seatJson.put("score", score);
//            seatJson.put("name", name);
//            seatJson.put("online", online);
//            seatJson.put("ready", seat1.isReady());
//            seatJson.put("seatindex", seat1.getSeatIndex());
//            seats.add(seatJson);
//
//            if (userId == seat1.getUserId()) {
//                userData = seatJson;
//            }
//        }
//        // 通知前端
//        ResultEntity resultEntity = ResultEntityUtil.ok();
//        resultEntity.add("roomid", roomInfo.getId());
//        resultEntity.add("conf", roomInfo.getConf());
//        resultEntity.add("numofgames", roomInfo.getNumOfGames());
//        resultEntity.add("seats", seats);
//
//        session.send("login_result", resultEntity.result());
//
//        // 通知其它客户端
//        this.userContext.broacastInRoom(userId, false, "new_user_comes_push", userData);
//
//        // 玩家上线，强制设置为TRUE
//        this.roomContext.setReady(userId, true);
//
//        session.send("login_finished");

        // TODO
        // if (roomInfo.dr != null) {
        // var dr = roomInfo.dr;
        // var ramaingTime = (dr.endTime - Date.now()) / 1000;
        // var data = {
        // time:ramaingTime,
        // states:dr.states
        // }
        // userMgr.sendMsg(userId, 'dissolve_notice_push', data);
        // }

    }

    /** 断开链接 */
    public void disconnect(Session session) {
        if (!session.isLogin()) {
            // 已经登陆过的就忽略
            return;
        }
        long userId = session.userId();

        JSONObject resultData = new JSONObject();
        resultData.put("userid", userId);
        resultData.put("online", false);

        // 通知其它客户端
        this.userContext.broacastInRoom(userId, false, "'user_state_push'", resultData);

        // 清除玩家的在线信息
        this.userContext.remove(userId);
    }

    /** ping */
    public void gamePing(Session session, Object data) {
        session.send("game_pong");
    }

}
