package com.liubowen.socketiomahjong.vo;

import com.liubowen.socketiomahjong.domain.game.GameType;
import lombok.Data;

/**
 * @author liubowen
 * @date 2017/12/17 23:56
 * @description
 */
@Data
public class GameConfVo {

    private String type;

    private int difen;

    private int zimo;

    private boolean jiangdui;

    private boolean huansanzhang;

    private int zuidafanshu;

    private int jushuxuanze;

    private int dianganghua;

    private boolean menqing;

    private boolean tiandihu;

    public GameType getGameType() {
        return GameType.valueOf0(this.type);
    }


}
