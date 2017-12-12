package com.liubowen.socketiomahjong.domain.room;

import com.liubowen.socketiomahjong.domain.game.GameType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/11/10 18:54
 * @description
 */
@Data
public class RoomConfig {

    private GameType gameType;

    private int button;

    private int index;

    private List<Integer> mahjongs;

    private Map<Integer, List<Integer>> gameSeats;

    private long creatorId;

    private int difen;

    private int zimo;

    private int jiangdui;

    private int huansanzhang;

    private int zuidafanshu;

    private int jushuxuanze;

    private int dianganghua;

    private int menqing;

    private int tiandihu;

    public RoomConfig decode(String data) {
        return this;
    }

    public String encode() {
        return "";
    }

}
