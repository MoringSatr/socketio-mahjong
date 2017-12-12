package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.domain.room.Room;

/**
 * @author liubowen
 * @date 2017/11/29 16:07
 * @description
 */
public interface RoomDomainService {

    ResultEntity createRoom(String account, long userId, String conf);

    ResultEntity enterRoom(long userId, String name,  String roomId);
}
