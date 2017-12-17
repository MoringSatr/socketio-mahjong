package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.common.ResultEntity;

/**
 * @author liubowen
 * @date 2017/12/18 16:20
 * @description
 */
public interface GameService {

    ResultEntity getServerInfo(String serverId, String sign);

    ResultEntity createRoom(long userId, String sign, int gems, String gameConfVoString);

    ResultEntity enterRoom(String serverId, String sign);

    ResultEntity ping(String serverId, String sign);

    ResultEntity isRoomRuning(String serverId, String sign);
}
