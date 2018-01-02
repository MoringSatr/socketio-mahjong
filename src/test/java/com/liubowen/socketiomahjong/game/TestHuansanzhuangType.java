package com.liubowen.socketiomahjong.game;

/**
 * @author liubowen
 * @date 2018/1/3 1:14
 * @description 换三张方式
 */
public enum TestHuansanzhuangType {

    /** 顺时针 */
    CLOCKWISE(1),
    /** 逆时针 */
    COUNTERCLOCKWISE(2),
    /** 对家互换 */
    OPPOSITE(3),;

    private int type;

    TestHuansanzhuangType(int type) {
        this.type = type;
    }
}
