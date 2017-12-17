package com.liubowen.socketiomahjong.repo.impl;

import com.liubowen.socketiomahjong.entity.RoomConfigInfo;
import com.liubowen.socketiomahjong.entity.RoomInfo;
import com.liubowen.socketiomahjong.entity.RoomPlayerInfo;
import com.liubowen.socketiomahjong.mapper.RoomConfigInfoMapper;
import com.liubowen.socketiomahjong.mapper.RoomInfoMapper;
import com.liubowen.socketiomahjong.mapper.RoomPlayerInfoMapper;
import com.liubowen.socketiomahjong.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/12/14 20:07
 * @description
 */
@Repository("roomRepo")
@Transactional
public class RoomRepoImpl implements RoomRepo {

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private RoomConfigInfoMapper roomConfigInfoMapper;

    @Autowired
    private RoomPlayerInfoMapper roomPlayerInfoMapper;

    @Override
    public RoomInfo findRoomInfoByRoomId(String roomId) {
        RoomInfo roomInfo = this.roomInfoMapper.findRoomInfoByRoomId(roomId);
        if (roomInfo == null) {
            return null;
        }
        RoomConfigInfo roomConfigInfo = this.roomConfigInfoMapper.selectByPrimaryKey(roomId);
        List<RoomPlayerInfo> roomPlayerInfos = this.roomPlayerInfoMapper.findRoomPlayerInfosByRoomId(roomId);
        roomInfo.setRoomConfigInfo(roomConfigInfo);
        roomInfo.setRoomPlayerInfos(roomPlayerInfos);
        return roomInfo;
    }

    @Override
    public boolean save(RoomInfo roomInfo) {
        RoomConfigInfo roomConfigInfo = roomInfo.getRoomConfigInfo();
        List<RoomPlayerInfo> roomPlayerInfos = roomInfo.getRoomPlayerInfos();
        if (roomConfigInfo != null) {
            this.roomConfigInfoMapper.insertSelective(roomConfigInfo);
        }
        if (roomPlayerInfos != null && !roomPlayerInfos.isEmpty()) {
            roomPlayerInfos.forEach(roomPlayerInfo -> this.roomPlayerInfoMapper.insertSelective(roomPlayerInfo));
        }
        this.roomInfoMapper.insert(roomInfo);
        return true;
    }

    @Override
    public boolean update(RoomInfo roomInfo) {
        RoomConfigInfo roomConfigInfo = roomInfo.getRoomConfigInfo();
        List<RoomPlayerInfo> roomPlayerInfos = roomInfo.getRoomPlayerInfos();
        if (roomConfigInfo != null) {
            this.roomConfigInfoMapper.updateByPrimaryKeySelective(roomConfigInfo);
        }
        if (roomPlayerInfos != null && !roomPlayerInfos.isEmpty()) {
            roomPlayerInfos.forEach(roomPlayerInfo -> this.roomPlayerInfoMapper.updateByPrimaryKeySelective(roomPlayerInfo));
        }
        this.roomInfoMapper.updateByPrimaryKeySelective(roomInfo);
        return true;
    }

    @Override
    public boolean delete(RoomInfo roomInfo) {
        RoomConfigInfo roomConfigInfo = roomInfo.getRoomConfigInfo();
        List<RoomPlayerInfo> roomPlayerInfos = roomInfo.getRoomPlayerInfos();
        if (roomConfigInfo != null) {
            this.roomConfigInfoMapper.delete(roomConfigInfo);
        }
        if (roomPlayerInfos != null && !roomPlayerInfos.isEmpty()) {
            roomPlayerInfos.forEach(roomPlayerInfo -> this.roomPlayerInfoMapper.delete(roomPlayerInfo));
        }
        this.roomInfoMapper.delete(roomInfo);
        return true;
    }
}
