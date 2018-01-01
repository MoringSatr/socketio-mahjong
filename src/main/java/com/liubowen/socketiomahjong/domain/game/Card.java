package com.liubowen.socketiomahjong.domain.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liubowen
 * @date 2017/12/24 16:48
 * @description
 */
@Slf4j
@Data
@AllArgsConstructor
public class Card {

    private MahjongType mahjongType;

    private int id;

    public int getType() {
        return this.mahjongType.getType();
    }

    public Card(int id) {
        this.mahjongType = getMJType(id);
        this.id = id;
    }

    public static MahjongType getMJType(int id) {
        if (id >= 0 && id < 9) {
            //筒
            return MahjongType.TONG;
        } else if (id >= 9 && id < 18) {
            //条
            return MahjongType.TIAO;
        } else if (id >= 18 && id < 27) {
            //万
            return MahjongType.WAN;
        }
        return MahjongType.FENG;
    }



}
