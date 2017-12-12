package com.liubowen.socketiomahjong.domain.room;

import lombok.Data;

/**
 * @author liubowen
 * @date 2017/11/10 11:14
 * @description 位子
 */
@Data
public class Seat {

    private RoomPlayer roomPlayer;

    private boolean ready;

    private int seatIndex;

    private int numZiMo;

    private int numJiePao;

    private int numDianPao;

    private int numAnGang;

    private int numMingGang;

    private int numChaJiao;

    public long getUserId() {
        if (this.roomPlayer == null) {
            return 0;
        }
        return this.roomPlayer.getUserId();
    }

    public boolean hasRoomPlayer() {
        return this.roomPlayer != null;
    }

    public void exitRoom() {
        this.roomPlayer = null;
        this.ready = false;
        this.numZiMo = 0;
        this.numJiePao = 0;
        this.numDianPao = 0;
        this.numAnGang = 0;
        this.numMingGang = 0;
        this.numChaJiao = 0;
    }
}
