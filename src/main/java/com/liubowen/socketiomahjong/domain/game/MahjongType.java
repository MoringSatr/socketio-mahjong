package com.liubowen.socketiomahjong.domain.game;

import lombok.Getter;

/**
 * @author liubowen
 * @date 2017/12/24 16:33
 * @description
 */
public enum MahjongType {
    /** 筒 */
    TONG(0),
    /** 条 */
    TIAO(1),
    /** 万 */
    WAN(2),
    /** 风 */
    FENG(4);

    @Getter
    private final int type;

    MahjongType(int type) {
        this.type = type;
    }

    public static MahjongType valueOf(int type) {
        for (MahjongType mahjongType : values()) {
            if (mahjongType.type == type) {
                return mahjongType;
            }
        }
        return null;
    }

    public static MahjongType getMJType(byte card) {
        if (card >= 0 && card < 9) {
            //筒
            return TONG;
        } else if (card >= 9 && card < 18) {
            //条
            return TIAO;
        } else if (card >= 18 && card < 27) {
            //万
            return WAN;
        }
        return FENG;
    }


}
