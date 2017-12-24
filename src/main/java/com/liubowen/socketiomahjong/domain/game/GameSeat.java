package com.liubowen.socketiomahjong.domain.game;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/24 16:59
 * @description
 */
@Data
@Slf4j
public class GameSeat {

    private int index;

    private long userId;

    private String roomId;

    /** 手牌 */
    private List<Card> holds;
    private Map<Integer, Integer> countMap;

    /** 打出的牌 */
    private List<Card> shoots;

    /** 杠的牌 */
    private List<Card> gangPai;

    /** 吃的牌 */

    /** 碰的牌 */
    private List<Card> pengs;

    /** 的牌 */
    private List<Card> wangangs;

    /** 的牌 */
    private List<Card> diangangs;

    /** 听的牌 */
    private Map<Integer, TingCard> tingMap;


    /** 缺德牌（不要的牌） */
    private MahjongType que;

    private boolean canPeng;

    private boolean canGang;

    private boolean canHu;

    private int lastFangGangSeat;


    public int getCardCount(int pai) {
        int count = 0;
        for (Card card : this.holds) {
            if (card.getId() == pai) {
                count++;
            }
        }
        return count;
    }

    List<Integer> getGangPais() {
        List<Integer> pais = Lists.newArrayList();
        this.gangPai.forEach(card -> {
            pais.add(card.getId());
        });
        return pais;
    }

}
