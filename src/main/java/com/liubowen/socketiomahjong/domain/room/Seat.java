package com.liubowen.socketiomahjong.domain.room;

import com.liubowen.socketiomahjong.entity.RoomPlayerInfo;
import lombok.Data;

/**
 * @author liubowen
 * @date 2017/11/10 11:14
 * @description 位子
 */
@Data
public class Seat {

    private RoomPlayerInfo roomPlayerInfo;

    private boolean ready;

    private int seatIndex;

    private int numZiMo;

    private int numJiePao;

    private int numDianPao;

    private int numAnGang;

    private int numMingGang;

    private int numChaJiao;

    private int score;

    private String ip;

    public long getUserId() {
        if (this.roomPlayerInfo == null) {
            return 0;
        }
        return this.roomPlayerInfo.getUserId();
    }

    public boolean hasRoomPlayer() {
        return this.roomPlayerInfo != null;
    }

    public void exitRoom() {
        this.roomPlayerInfo = null;
        this.ready = false;
        this.numZiMo = 0;
        this.numJiePao = 0;
        this.numDianPao = 0;
        this.numAnGang = 0;
        this.numMingGang = 0;
        this.numChaJiao = 0;
    }

    public String getName() {
        return this.roomPlayerInfo.getName();
    }
}
