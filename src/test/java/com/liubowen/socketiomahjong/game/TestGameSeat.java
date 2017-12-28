package com.liubowen.socketiomahjong.game;

import com.liubowen.socketiomahjong.domain.game.MahjongType;
import com.liubowen.socketiomahjong.domain.game.TingCard;

import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/29 1:48
 * @description
 */
public class TestGameSeat {

    /** 座位index */
    private int index;

    /** 座位上玩家信息 */
    private TestSeatPlayer seatPlayer;

    /** 手牌 */
    private List<Integer> holds;

    /** 打出的牌 */
    private List<Integer> shoots;

    /** 杠的牌 */
    private List<Integer> gangPai;

    /** 吃的牌 */

    /** 碰的牌 */
    private List<Integer> pengs;

    /** 的牌 */
    private List<Integer> wangangs;

    /** 的牌 */
    private List<Integer> diangangs;

    /** 听的牌 */
    private Map<Integer, TingCard> tingMap;

    /** 缺德牌（不要的牌） */
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
