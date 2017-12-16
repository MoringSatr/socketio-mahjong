package com.liubowen.socketiomahjong.repo.impl;

import com.google.common.collect.Lists;
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

import java.util.List;

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
        RoomInfo roomInfo = this.roomRepo.findRoomInfoByRoomId("4");
        log.info("roomInfo : {}", roomInfo);
    }

    @Test
    public void save() {
        String roomId = "4";
        RoomConfigInfo roomConfigInfo = new RoomConfigInfo();
        roomConfigInfo.setRoomId(roomId);
        roomConfigInfo.setGameType(GameType.KWX);

        RoomInfo roomInfo = new RoomInfo(roomId, "127.0.0.1", 9009, roomConfigInfo);

        RoomPlayerInfo roomPlayerInfo1 = new RoomPlayerInfo(11111, "张一", 100, roomId);
        RoomPlayerInfo roomPlayerInfo2 = new RoomPlayerInfo(22222, "张二", 200, roomId);
        RoomPlayerInfo roomPlayerInfo3 = new RoomPlayerInfo(33333, "张三", 300, roomId);
        RoomPlayerInfo roomPlayerInfo4 = new RoomPlayerInfo(44444, "张四", 400, roomId);
        List<RoomPlayerInfo> roomPlayerInfos = Lists.newArrayList(roomPlayerInfo1, roomPlayerInfo2, roomPlayerInfo3, roomPlayerInfo4);

        roomInfo.setRoomPlayerInfos(roomPlayerInfos);
        this.roomRepo.save(roomInfo);
    }

    @Test
    public void update() {
        RoomInfo roomInfo = this.roomRepo.findRoomInfoByRoomId("4");
        log.info("old roomInfo : {}", roomInfo);
        RoomConfigInfo roomConfigInfo = roomInfo.getRoomConfig();
        roomConfigInfo.setButton(11);
        List<RoomPlayerInfo> roomPlayerInfos = roomInfo.getRoomPlayerInfos();
        roomPlayerInfos.forEach(roomPlayerInfo -> roomPlayerInfo.setScore(roomPlayerInfo.getScore() + 10));
        this.roomRepo.update(roomInfo);
        log.info("new roomInfo : {}", roomInfo);
    }

    @Test
    public void delete() {
        RoomInfo roomInfo = this.roomRepo.findRoomInfoByRoomId("4");
        log.info("old roomInfo : {}", roomInfo);
        this.roomRepo.delete(roomInfo);
    }
}