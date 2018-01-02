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

    private int num;

    public int getType() {
        return this.mahjongType.getType();
    }

    public Card(int id) {
        this.mahjongType = getMJType(id);
        this.id = id;
        this.initNumById();
    }

    public Card(MahjongType mahjongType, int id) {
        this.mahjongType = mahjongType;
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

    private void initNumById() {
        if (id >= 0 && id < 9) {
            this.num = id + 1;
            //筒
        } else if (id >= 9 && id < 18) {
            this.num = id - 8;
            //条
        } else if (id >= 18 && id < 27) {
            this.num = id - 17;
            //万
        }
    }

    @Override
    public String toString() {
        return this.num + "[" + this.mahjongType.name() + "]";
    }
}
