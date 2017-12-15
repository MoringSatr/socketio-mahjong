package com.liubowen.socketiomahjong.repo;

import com.liubowen.socketiomahjong.common.ServiceDataRepo;
import com.liubowen.socketiomahjong.entity.RoomInfo;

/**
 * @author liubowen
 * @date 2017/12/14 20:07
 * @description
 */
public interface RoomRepo extends ServiceDataRepo<RoomInfo> {

    RoomInfo findRoomInfoByRoomId(String roomId);

}
