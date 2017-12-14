package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liubowen
 * @date 2017/12/12 10:13
 * @description 房间中的玩家信息
 */
@Data
@Table(name = "room_player")
public class RoomPlayerInfo {

    /** 玩家id */
    @Id
    private long userId;

    /** 玩家名字 */
    @Column(name = "name")
    private String name;

    /** 玩家积分 */
    @Column(name = "score")
    private int score;

    /** 位子索引 */
    @Column(name = "seat_index")
    private int seatIndex;

    /** 房间id */
    @Column(name = "room_id")
    private String roomId;

    public RoomPlayerInfo(long userId, String name, int score) {
        this.userId = userId;
        this.name = name;
        this.score = score;
    }

}
