package com.liubowen.socketiomahjong.domain.room;

import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.domain.game.*;
import com.liubowen.socketiomahjong.entity.RoomInfo;
import lombok.Data;

import java.util.Map;

/**
 * @author liubowen
 * @date 2017/11/10 10:52
 * @description
 */
@Data
public class Room {

    private String uuid;

    private String id;

    private int numOfGames;

    private long createTime;

    private int nextButton;

    private Map<Integer, Seat> seats;

    private RoomConfig conf;

    private GameManager gameManager;

    public Room(RoomInfo roomInfo) {
        this.uuid = roomInfo.getUuid();
        this.id = roomInfo.getId();
        this.numOfGames = roomInfo.getNumOfTurns();
        this.createTime = roomInfo.getCreateTime();
        this.nextButton = roomInfo.getNextButton();
        this.seats = Maps.newHashMap();
        this.conf = new RoomConfig();
        this.conf.decode(roomInfo.getBaseInfo());
        if(conf.getGameType() == GameType.XLCH) {
            this.gameManager = new XLCHGameManager();
        }else if(conf.getGameType() == GameType.XZDD){
            this.gameManager = new XZDDGameManager();
        }else if(conf.getGameType() == GameType.KWX){
            this.gameManager = new KWXGameManager();
        }
        initSeat(roomInfo);
    }

    private void initSeat(RoomInfo roomInfo) {
        for(int i = 0; i < 4; i++){
            Seat seat = new Seat();
            seat.setSeatIndex(i);
            if(i == 0) {
                seat.setUserId(roomInfo.getUserId0());
                seat.setName(roomInfo.getUserName0());
                seat.setScore(roomInfo.getUserScore0());
            }else if(i == 1) {
                seat.setUserId(roomInfo.getUserId1());
                seat.setName(roomInfo.getUserName1());
                seat.setScore(roomInfo.getUserScore1());
            }else if(i == 2) {
                seat.setUserId(roomInfo.getUserId2());
                seat.setName(roomInfo.getUserName2());
                seat.setScore(roomInfo.getUserScore2());
            }else if(i == 3) {
                seat.setUserId(roomInfo.getUserId3());
                seat.setName(roomInfo.getUserName3());
                seat.setScore(roomInfo.getUserScore3());
            }
            seat.setSeatIndex(i);
            this.seats.put(seat.getSeatIndex(), seat);
        }
    }
}
