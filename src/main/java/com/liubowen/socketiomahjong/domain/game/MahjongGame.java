package com.liubowen.socketiomahjong.domain.game;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/24 16:40
 * @description
 */
@Data
@Slf4j
public class MahjongGame {

    private List<Card> mahjongs;

    private int currentIndex;

    private Map<Integer, GameSeat> seats;

    private int lastHuPaiSeat;

    /** 庄家位子 */
    private int button;

    private Object qiangGangContext;

    private int fangpaoshumu;

    private int turn;

    public GameSeat getGameSeat(int index) {
        return this.seats.get(index);
    }

    public Card mopai() {
        Card card = this.mahjongs.get(this.currentIndex);
        this.currentIndex++;
        return card;
    }

}
