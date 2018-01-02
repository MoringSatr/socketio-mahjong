package com.liubowen.socketiomahjong.game;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.domain.game.Card;
import org.apache.commons.lang3.RandomUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/29 1:48
 * @description 麻将牌 0-8筒 9-17条 18-26万  -1东 -2南 -3西 -4北 -5中 -6发 -7白
 */
public class TestGame {

    private Map<Integer, TestRoomSeatData> testRoomSeatDataMap;

    private TestGameConfig testGameConfig;

    private List<Byte> pais;

    private TestHuansanzhuangType testHuansanzhuangType;

    /** 当前局数 */
    private int turn;

    public TestGame(TestGameConfig testGameConfig) {
        this.testGameConfig = testGameConfig;
        this.testRoomSeatDataMap = Maps.newHashMap();
        this.turn = 0;
        this.pais = Lists.newArrayList();
    }

    public TestRoomSeatData getTestRoomSeatData(int seatIndex) {
        return this.testRoomSeatDataMap.get(seatIndex);
    }

    public TestRoomSeatData getTestRoomSeatDataByplayerId(long playerId) {
        for (TestRoomSeatData testRoomSeatData : this.testRoomSeatDataMap.values())
            if (testRoomSeatData.getPlayerId() == playerId) {
                return testRoomSeatData;
            }
        return null;
    }

    public void addTestRoomSeatData(TestRoomSeatData testRoomSeatData) {
        this.testRoomSeatDataMap.put(testRoomSeatData.getIndex(), testRoomSeatData);
    }

    public void palyerReady(int seatIndex) {
        TestRoomSeatData testRoomSeatData = this.getTestRoomSeatData(seatIndex);
        testRoomSeatData.ready();
    }

    /** 玩家摸牌 */
    public void playerMopai(int seatIndex) {
        byte pai = mopai();
        TestRoomSeatData testRoomSeatData = this.getTestRoomSeatData(seatIndex);
        testRoomSeatData.mopai(pai);
    }

    /** 摸牌 */
    private byte mopai() {
        return pais.remove(0);
    }

    /** 是否还有牌 */
    public boolean hasPai() {
        return this.leftPaiCount() == 0;
    }

    /** 剩余牌的数量 */
    public int leftPaiCount() {
        return this.pais.size();
    }

    /** 玩家打牌 */
    public byte playerDapai(int seatIndex, byte pai) {
        TestRoomSeatData testRoomSeatData = this.getTestRoomSeatData(seatIndex);
        return testRoomSeatData.dapai(pai);
    }

    /** 初始化牌 */
    public void initPais() {
        //  麻将牌 0-8筒 9-17条 18-26万  -1东 -2南 -3西 -4北 -5中 -6发 -7白
        this.pais = Lists.newArrayList();

        //  加入筒子派
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j < 4; j++) {
                this.pais.add((byte) i);
            }
        }

        //  加入万字牌
        for (int i = 9; i <= 17; i++) {
            for (int j = 0; j < 4; j++) {
                this.pais.add((byte) i);
            }
        }

        //  加入条子牌
        for (int i = 18; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                this.pais.add((byte) i);
            }
        }

//        //  加入东南西北
//        for (int i = -4; i <= -1; i++) {
//            for (int j = 0; j < 4; j++) {
//                this.pais.add((byte) i);
//            }
//        }
//
//        //  加入中发白
//        for (int i = -7; i <= -5; i++) {
//            for (int j = 0; j < 4; j++) {
//                this.pais.add((byte) i);
//            }
//        }

    }

    /** 洗牌 */
    private void shufflePais() {
        int shuffleTimes = RandomUtils.nextInt(1, 8);
        System.err.println("shuffleTimes : " + shuffleTimes);
        for (int i = 0; i < shuffleTimes; i++) {
            Collections.shuffle(pais);
        }
    }

    /** 开始游戏 */
    public void startGame() {
        this.turn += 1;
        System.err.println("turn : " + turn);
        this.initPais();
        this.shufflePais();
        List<Card> cards = Lists.newArrayList();
        this.pais.forEach(pai -> {
            Card card = new Card(pai);
            cards.add(card);
        });
//        System.err.println("pais : " + Arrays.toString(cards.toArray()));
    }

    /** 换三张 */
    public void huansanzhang() {
        if (TestHuansanzhuangType.CLOCKWISE == this.testHuansanzhuangType) {

        } else if (TestHuansanzhuangType.COUNTERCLOCKWISE == this.testHuansanzhuangType) {

        } else {

        }
    }

    /** 随机换三张方式 */
    public void randomHuansnazhangType() {
        int type = RandomUtils.nextInt(0, TestHuansanzhuangType.values().length);
        this.testHuansanzhuangType = TestHuansanzhuangType.values()[type];
    }

    /** 玩家移除换三张的牌 */
    public void playerRemoveHuansanzhangPais(int seatIndex, List<Byte> pais) {
        TestRoomSeatData testRoomSeatData = this.getTestRoomSeatData(seatIndex);
        testRoomSeatData.removeHuansanzhangPais(pais);
    }

    /** 玩家得到换的三张牌 */
    public void playerGetHuansanzhangPais(int seatIndex, List<Byte> pais) {
        TestRoomSeatData testRoomSeatData = this.getTestRoomSeatData(seatIndex);
        testRoomSeatData.getHuansanzhangPais(pais);
    }


    public static void main(String[] args) throws Exception {
//        TestGame testGame = new TestGame(null);
//        testGame.startGame();
//        List<Byte> pais = Lists.newArrayList();
//        for (int i = 0; i < 13; i++) {
//            byte pai = testGame.mopai();
//            pais.add(pai);
//        }
//        Collections.sort(pais);
//        List<Card> cards = Lists.newArrayList();
//        pais.forEach(pai -> {
//            Card card = new Card(pai);
//            cards.add(card);
//        });
//        System.err.println("pais : " + Arrays.toString(cards.toArray()));

    }
}

