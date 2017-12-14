package com.liubowen.socketiomahjong.repo;

import com.liubowen.socketiomahjong.entity.RoomInfo;

/**
 * @author liubowen
 * @date 2017/12/14 20:07
 * @description
 */
public interface RoomRepo {

    RoomInfo findRoomInfoByRoomId(String roomId);

    boolean updateRoomInfo(RoomInfo roomInfo);

    boolean deleteRoomInfo(RoomInfo roomInfo);
}
