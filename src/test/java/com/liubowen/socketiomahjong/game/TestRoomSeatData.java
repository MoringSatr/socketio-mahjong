package com.liubowen.socketiomahjong.game;

import com.liubowen.socketiomahjong.domain.game.MahjongType;

import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/29 1:48
 * @description
 */
public class TestRoomSeatData {

    /** 座位index */
    private int index;

    /** 座位上玩家信息 */
    private TestSeatPlayer seatPlayer;

    /** 手牌 */
    private List<Byte> holds;

    /** 打出的牌 */
    private List<Byte> shoots;

    /** 吃的牌 */
    private List<Byte> chis;

    /** 碰的牌 */
    private List<Byte> pengs;

    /** 杠的牌 */
    private List<Byte> angangs;

    /** 弯杠的牌的牌 */
    private List<Byte> wangangs;

    /** 点杠的牌的牌 */
    private List<Byte> diangangs;

    /** 听的牌 */
    private Map<Integer, TestTingCard> tingMap;

    /** 缺的牌（不要的牌） */
    private MahjongType que;


    /** 手牌 */

    /** 打出的牌 */

    /** 暗杠的牌 */

    /** 明杠的牌 */

    /** 弯杠的牌 */

    /** 吃的牌 */

    /** 碰的牌 */

    /** 听的牌 */

    /** 缺德牌（不要的牌） */

    /** 自摸次数 */

    /** 点炮次数 */

    /** 劫炮次数 */

}
