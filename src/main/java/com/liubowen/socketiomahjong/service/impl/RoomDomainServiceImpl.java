package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.domain.room.Room;
import com.liubowen.socketiomahjong.service.RoomDomainService;
import org.springframework.stereotype.Service;

/**
 * @author liubowen
 * @date 2017/11/29 16:07
 * @description
 */
@Service("roomDomainService")
public class RoomDomainServiceImpl implements RoomDomainService {

    @Override
    public Room createRoom(String account, long userId, String conf) {
        return null;
    }
}
