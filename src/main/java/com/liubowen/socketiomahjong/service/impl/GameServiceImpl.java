package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.constant.Constant.RoomConstant;
import com.liubowen.socketiomahjong.domain.room.RoomContext;
import com.liubowen.socketiomahjong.domain.user.TokenContext;
import com.liubowen.socketiomahjong.domain.user.UserToken;
import com.liubowen.socketiomahjong.dto.EnterInfoDto;
import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.service.GameService;
import com.liubowen.socketiomahjong.test.TestRoom;
import com.liubowen.socketiomahjong.test.TestRoomContext;
import com.liubowen.socketiomahjong.test.TestSeatPlayer;
import com.liubowen.socketiomahjong.util.encode.Md5Util;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import com.liubowen.socketiomahjong.vo.GameConfVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubowen
 * @date 2017/12/18 16:24
 * @description
 */
@Service("gameService")
public class GameServiceImpl implements GameService {

    @Autowired
    private RoomContext roomContext;

    @Autowired
    private TokenContext tokenContext;

    @Autowired
    private TestRoomContext testRoomContext;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResultEntity getServerInfo(String serverId, String sign) {
        return null;
    }

    @Override
    public ResultEntity createRoom(long userId, String sign, int gems, String gameConfVoString) {
        if (userId == 0 || StringUtils.isBlank(sign) || gems == 0 || gameConfVoString == null) {
            return ResultEntityUtil.err("invalid parameters");
        }
        String md5 = Md5Util.MD5(userId + gameConfVoString + gems + Constant.ROOM_PRI_KEY);
        if (!sign.equals(md5)) {
            return ResultEntityUtil.err("sign check failed.");
        }
        GameConfVo gameConfVo = (GameConfVo) JSONObject.toBean(JSONObject.fromObject(gameConfVoString), GameConfVo.class);

//        Room room = this.roomContext.createRoom(gameConfVo, gems, RoomConstant.ROOM_SERVER_IP, RoomConstant.ROOM_SERVER_PORT);
//        if (room == null) {
//            return ResultEntityUtil.err("create room failed.");
//        }
//        String roomId = room.getId();

        TestRoom room = this.testRoomContext.getTestRoom();
        ResultEntity resultEntity = ResultEntityUtil.ok();
        resultEntity.add("roomId", room.getId());
        return resultEntity;
    }

    @Override
    public ResultEntity enterRoom(long userId, String name, String roomId, String sign) {
        if (userId == 0 || StringUtils.isBlank(roomId) || StringUtils.isBlank(sign)) {
            return ResultEntityUtil.err("invalid parameters");
        }
        String md5 = Md5Util.MD5(userId + name + roomId + Constant.ROOM_PRI_KEY);
        if (!sign.equals(md5)) {
            return ResultEntityUtil.err("sign check failed.");
        }

        UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null) {
            return ResultEntityUtil.err("sign check failed.");
        }
        //  安排玩家坐下
//        this.roomContext.enterRoom(roomId, userId, name);
        TestRoom room = this.testRoomContext.getTestRoom();
        TestSeatPlayer testSeatPlayer = new TestSeatPlayer(userInfo.getUserId(), userInfo.getName(), userInfo.getHeadImg(), 0, "192.168.3.2");
        room.addPlayer(testSeatPlayer);

        UserToken userToken = this.tokenContext.createToken(userId, 5000L);
        if (userToken == null) {
            return ResultEntityUtil.err("create userToken failed.");
        }

        String token = userToken.getToken();
        ResultEntity resultEntity = ResultEntityUtil.ok();
        EnterInfoDto enterInfoDto = new EnterInfoDto(roomId, RoomConstant.ROOM_SERVER_IP, RoomConstant.ROOM_SERVER_PORT, token);
        resultEntity.add("enterInfo", enterInfoDto);
        return resultEntity;
    }

    @Override
    public ResultEntity ping(String serverId, String sign) {
        return null;
    }

    @Override
    public ResultEntity isRoomRuning(String serverId, String sign) {
        return null;
    }
}
