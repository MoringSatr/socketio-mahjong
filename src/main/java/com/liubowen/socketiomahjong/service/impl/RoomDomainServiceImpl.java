package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.service.GameService;
import com.liubowen.socketiomahjong.service.RoomDomainService;
import com.liubowen.socketiomahjong.util.encode.Md5Util;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import com.liubowen.socketiomahjong.vo.GameConfVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubowen
 * @date 2017/11/29 16:07
 * @description
 */
@Service("roomDomainService")
public class RoomDomainServiceImpl implements RoomDomainService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private GameService gameService;

    @Override
    public ResultEntity createRoom(String account, long userId, GameConfVo conf) {
        UserInfo userInfo = this.userInfoMapper.findUserInfoByAccount(account);
        if (userInfo == null) {
            return ResultEntityUtil.err("userInfo not exist");
        }
        int userGems = userInfo.getGems();
        String gameConfVoString = JSONObject.fromObject(conf).toString();
        String md5 = Md5Util.MD5(userId + gameConfVoString + userGems + Constant.ROOM_PRI_KEY);
        //  TODO
        ResultEntity createRoomResult = this.gameService.createRoom(userId, md5, userGems, gameConfVoString);
        return createRoomResult;
    }

    @Override
    public ResultEntity enterRoom(long userId, String name, String roomId) {
        String reqSign = Md5Util.MD5(userId + name + roomId + Constant.ROOM_PRI_KEY);
        //  TODO    checkRoomIsRuning

        //TODO enterRoomReq
        ResultEntity enterRoomResult = this.gameService.enterRoom(userId, name, roomId, reqSign);
        if (!enterRoomResult.ok()) {
            return enterRoomResult;
        }

        return enterRoomResult;
    }
}
