package com.liubowen.socketiomahjong.domain.game;

import lombok.Getter;

/**
 * @author liubowen
 * @date 2017/11/10 18:55
 * @description
 */
public enum GameType {

    /** 血流成河 */
    XLCH("xlch"),
    /** 血战到底 */
    XZDD("xzdd"),
    /** 卡五星 */
    KWX("kwx"),;

    GameType(String type) {
        this.type = type;
    }

    @Getter
    private final String type;

    public static GameType valueOf0(String type) {
        for (GameType gameType : values()) {
            if (gameType.getType().equals(type)) {
                return gameType;
            }
        }
        return null;
    }

}
