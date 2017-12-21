package com.liubowen.socketiomahjong.domain.room;

import com.liubowen.socketiomahjong.common.Saveable;
import com.liubowen.socketiomahjong.domain.game.*;
import com.liubowen.socketiomahjong.entity.RoomConfigInfo;
import com.liubowen.socketiomahjong.entity.RoomInfo;
import com.liubowen.socketiomahjong.entity.RoomPlayerInfo;
import lombok.Getter;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/10 10:52
 * @description
 */
public class Room implements Saveable {

    private String uuid;

    @Getter
    private String id;

    @Getter
    private int numOfGames;

    private long createTime;

    private int nextButton;

    /**
     * 房间位子信息
     */
    private Seats seats;

    /**
     * 房间配置信息
     */
    @Getter
    private RoomConfigInfo conf;

    /**
     * 房间游戏信息
     */
    private GameManager gameManager;

    public Room(RoomInfo roomInfo) {
        this.uuid = roomInfo.getUuid() + "";
        this.id = roomInfo.getId();
        this.numOfGames = roomInfo.getNumOfTurns();
        this.createTime = roomInfo.getCreateTime();
        this.nextButton = roomInfo.getNextButton();
        this.conf = roomInfo.getRoomConfigInfo();
        this.seats = new Seats(roomInfo);
        initGameManager();
    }

    @Override
    public void save() {

    }

    private void initGameManager() {
        if (conf.getGameType() == GameType.XLCH) {
            this.gameManager = new XLCHGameManager();
        } else if (conf.getGameType() == GameType.XZDD) {
            this.gameManager = new XZDDGameManager();
        } else if (conf.getGameType() == GameType.KWX) {
            this.gameManager = new KWXGameManager();
        }
    }

    public List<Seat> allSeat() {
        return this.seats.allSeat();
    }

    public List<RoomPlayerInfo> allRoomPlayer() {
        return this.seats.allRoomPlayer();
    }

    public boolean isCreator(long userId) {
        return this.conf.getCreatorId() == userId;
    }

    public Seat getSeatByUserId(long userId) {
        return this.seats.getSeatByUserId(userId);
    }

    public void exitRoom(long userId) {
        this.seats.exitRoom(userId);
    }

    public void enterRoom(RoomPlayerInfo roomPlayerInfo) {
        if (this.isFull()) {
            return;
        }
        int idleSeatIndex = this.idleSeatIndex();
        roomPlayerInfo.setSeatIndex(idleSeatIndex);
        this.seats.putRoomPLayerOnSeat(roomPlayerInfo);
    }

    private boolean isFull() {
        for (Seat seat : this.allSeat()) {
            if (!seat.hasRoomPlayer()) {
                return false;
            }
        }
        return true;
    }

    private int idleSeatIndex() {
        for (Seat seat : this.allSeat()) {
            if (!seat.hasRoomPlayer()) {
                return seat.getSeatIndex();
            }
        }
        return -1;
    }

    public boolean hasUser(long userId) {
        return this.seats.hasUser(userId);
    }
}
