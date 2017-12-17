package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.domain.room.RoomContext;
import com.liubowen.socketiomahjong.service.GameService;
import com.liubowen.socketiomahjong.util.encode.Md5Util;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import com.liubowen.socketiomahjong.vo.GameConfVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liubowen
 * @date 2017/12/18 16:24
 * @description
 */
public class GameServiceImpl implements GameService {

    @Autowired
    private RoomContext roomContext;

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
        this.roomContext.createRoom(gameConfVo, gems, null, 0);
        return null;
    }

    @Override
    public ResultEntity enterRoom(String serverId, String sign) {
        return null;
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
