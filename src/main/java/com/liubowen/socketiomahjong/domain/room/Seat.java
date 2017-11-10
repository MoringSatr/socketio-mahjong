package com.liubowen.socketiomahjong.domain.room;

import lombok.Data;

/**
 * @author liubowen
 * @date 2017/11/10 11:14
 * @description
 */
@Data
public class Seat {

    private Long userId;

    private int score;

    private String name;

    private boolean ready;

    private int seatIndex;

    private int numZiMo;

    private int numJiePao;

    private int numDianPao;

    private int numAnGang;

    private int numMingGang;

    private int numChaJiao;

}
