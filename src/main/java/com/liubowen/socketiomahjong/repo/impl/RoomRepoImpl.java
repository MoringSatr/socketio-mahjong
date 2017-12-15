package com.liubowen.socketiomahjong.repo.impl;

import com.liubowen.socketiomahjong.entity.RoomInfo;
import com.liubowen.socketiomahjong.mapper.RoomInfoMapper;
import com.liubowen.socketiomahjong.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author liubowen
 * @date 2017/12/14 20:07
 * @description
 */
@Repository("roomRepo")
public class RoomRepoImpl implements RoomRepo {

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Override
    public RoomInfo findRoomInfoByRoomId(String roomUuid) {
        RoomInfo roomInfo = this.roomInfoMapper.selectByPrimaryKey(roomUuid);
        return roomInfo;
    }

    @Override
    public boolean save(RoomInfo roomInfo) {
        return false;
    }

    @Override
    public boolean update(RoomInfo roomInfo) {
        return false;
    }

    @Override
    public boolean delete(RoomInfo roomInfo) {
        return false;
    }
}
