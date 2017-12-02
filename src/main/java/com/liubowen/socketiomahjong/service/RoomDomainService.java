package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.domain.room.Room;

/**
 * @author liubowen
 * @date 2017/11/29 16:07
 * @description
 */
public interface RoomDomainService {

    Room createRoom(String account, long userId, String conf);
}
