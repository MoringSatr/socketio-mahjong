package com.liubowen.socketiomahjong.domain.room;

import lombok.Data;

/**
 * @author liubowen
 * @date 2017/12/12 10:13
 * @description 房间中的玩家信息
 */
@Data
public class RoomPlayer {

    /** 玩家id */
    private long userId;

    /** 玩家名字 */
    private String name;

    /** 玩家积分 */
    private int score;

    /** 位子索引 */
    private int seatIndex;

    public RoomPlayer(long userId, String name, int score) {
        this.userId = userId;
        this.name = name;
        this.score = score;
    }

}
