package com.liubowen.socketiomahjong.repo.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.domain.game.GameType;
import com.liubowen.socketiomahjong.entity.RoomConfigInfo;
import com.liubowen.socketiomahjong.entity.RoomInfo;
import com.liubowen.socketiomahjong.entity.RoomPlayerInfo;
import com.liubowen.socketiomahjong.mapper.RoomPlayerInfoMapper;
import com.liubowen.socketiomahjong.repo.RoomRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RoomRepoImplTest {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private RoomPlayerInfoMapper roomPlayerInfoMapper;

    @Test
    public void findRoomInfoByRoomId() {
        RoomInfo roomInfo = this.roomRepo.findRoomInfoByRoomId("1");
        log.info("roomInfo : {}", roomInfo);
    }

    @Test
    public void save() {
        String roomId = "1";
        RoomConfigInfo roomConfigInfo = new RoomConfigInfo();
        roomConfigInfo.setRoomId(roomId);
        roomConfigInfo.setGameType(GameType.KWX);

        RoomInfo roomInfo = new RoomInfo(roomId, "127.0.0.1", 9009, roomConfigInfo);

        RoomPlayerInfo roomPlayerInfo1 = new RoomPlayerInfo(11, "张一", 100, roomId);
        RoomPlayerInfo roomPlayerInfo2 = new RoomPlayerInfo(22, "张二", 200, roomId);
        RoomPlayerInfo roomPlayerInfo3 = new RoomPlayerInfo(33, "张三", 300, roomId);
        RoomPlayerInfo roomPlayerInfo4 = new RoomPlayerInfo(44, "张四", 400, roomId);
        List<RoomPlayerInfo> roomPlayerInfos = Lists.newArrayList(roomPlayerInfo1, roomPlayerInfo2, roomPlayerInfo3, roomPlayerInfo4);

        roomInfo.setRoomPlayerInfos(roomPlayerInfos);
        this.roomRepo.save(roomInfo);
    }

    @Test
    public void update() {
        List<RoomPlayerInfo> roomPlayerInfos = this.roomPlayerInfoMapper.findRoomPlayerInfosByRoomId("1");
        log.info("roomPlayerInfos : {}", roomPlayerInfos);
    }

    @Test
    public void delete() {
        List<RoomPlayerInfo> roomPlayerInfos = this.roomPlayerInfoMapper.findRoomPlayerInfosByUserId(11L);
        log.info("roomPlayerInfos : {}", roomPlayerInfos);

        RoomPlayerInfo roomPlayerInfo = this.roomPlayerInfoMapper.selectByPrimaryKey(11L);
        log.info("roomPlayerInfo : {}", roomPlayerInfo);
    }
}