package com.liubowen.socketiomahjong.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.domain.game.MahjongType;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liubowen
 * @date 2017/12/29 1:48
 * @description
 */
@Data
public class TestRoomSeatData {

    /** 座位index */
    private int index;

    /** 座位上玩家信息 */
    private TestSeatPlayer seatPlayer;

    /** 手牌 */
    private List<Byte> holds;

    /** 打出的牌 */
    private List<Byte> shoots;

    /** 碰的牌 */
    private List<Byte> pengs;

    /** 暗杠的牌 */
    private List<Byte> angangs;

    /** 弯杠的牌的牌 */
    private List<Byte> wangangs;

    /** 点杠的牌的牌 */
    private List<Byte> diangangs;

    /** 听的牌 */
    private Map<Byte, TestTingCard> tingMap;

    /** 缺的牌（不要的牌） */
    private MahjongType que;

    /** 自摸次数 */
    private int zimoNum;

    /** 点炮次数 */
    private int dianpaoNum;

    /** 劫炮次数 */
    private int jiepaoNum;

    /** 是否准备 */
    private boolean ready;

    /** 换三张拿出的牌 */
    private List<Byte> huansanzhangRemovePais;

    /** 换三张得到的牌 */
    private List<Byte> huansanzhangGetPais;

    public TestRoomSeatData(int index, TestSeatPlayer seatPlayer) {
        this.index = index;
        this.seatPlayer = seatPlayer;
        this.holds = Lists.newArrayList();
        this.shoots = Lists.newArrayList();
        this.pengs = Lists.newArrayList();
        this.angangs = Lists.newArrayList();
        this.wangangs = Lists.newArrayList();
        this.diangangs = Lists.newArrayList();
        this.tingMap = Maps.newHashMap();
    }

    public TestRoomSeatData(int index) {
        this.index = index;
        this.seatPlayer = null;
        this.holds = Lists.newArrayList();
        this.shoots = Lists.newArrayList();
        this.pengs = Lists.newArrayList();
        this.angangs = Lists.newArrayList();
        this.wangangs = Lists.newArrayList();
        this.diangangs = Lists.newArrayList();
        this.tingMap = Maps.newHashMap();
    }

    public boolean hasPlayer() {
        return this.seatPlayer != null;
    }

    /** 摸牌 */
    public void mopai(byte pai) {
        this.holds.add(pai);
    }

    /** 打牌 */
    public byte dapai(byte pai) {
        return this.holds.remove(pai);
    }

    /** 手牌某张牌数量 */
    public int holdPaiCount(byte pai) {
        AtomicInteger count = new AtomicInteger(0);
        this.holds.forEach(holdPai -> {
            if (holdPai == pai) {
                count.incrementAndGet();
            }
        });
        return count.get();
    }

    public void addTingpai(byte pai, int fun, String pattern) {
        TestTingCard testTingCard = new TestTingCard(pai, fun, pattern);
        this.tingMap.put(pai, testTingCard);
    }

    public void ready() {
        this.ready = true;
    }

    public long getPlayerId() {
        if (!this.hasPlayer()) {
            return 0;
        }
        return this.seatPlayer.getPlayerId();
    }

    public int getScore() {
        if (!this.hasPlayer()) {
            return 0;
        }
        return this.seatPlayer.getScore();
    }

    public String getHeadImage() {
        return this.seatPlayer.getHeadImage();
    }

    public String getNickname() {
        return this.seatPlayer.getNickname();
    }

    /** 改变玩家积分 */
    public void changeScore(int score) {
        this.seatPlayer.setScore((this.getScore() + score));
    }

    /** 获取玩家手牌某一类型牌的数量 */
    public int getPaiTypeCount(MahjongType mahjongType) {
        AtomicInteger count = new AtomicInteger(0);
        this.holds.forEach(pai -> {
            MahjongType type = TestGameUtil.getMJType(pai);
            if (mahjongType == type) {
                count.incrementAndGet();
            }
        });
        return count.get();
    }

    /** 获取可以换三张的牌型 */
    public List<Integer> getCanHuansnazhangTypes() {
        List<Integer> mahjongTypes = Lists.newArrayList();
        for (MahjongType mahjongType : MahjongType.values()) {
            if (mahjongType == MahjongType.FENG) {
                continue;
            }
            int count = getPaiTypeCount(mahjongType);
            if (count < 3) {
                continue;
            }
            mahjongTypes.add(mahjongType.getType());
        }
        return mahjongTypes;
    }

    /** 移除换三张的牌 */
    public void removeHuansanzhangPais(List<Byte> pais) {
        pais.forEach(pai -> {
            this.holds.remove(pai);
            this.huansanzhangRemovePais.add(pai);
        });
    }

    /** 得到换的三张牌 */
    public void getHuansanzhangPais(List<Byte> pais) {
        pais.forEach(pai -> {
            this.holds.add(pai);
            this.huansanzhangGetPais.add(pai);
        });
    }

    public void dingque(MahjongType mahjongType) {
        this.que = mahjongType;
    }

    public void addPlayer(TestSeatPlayer testSeatPlayer) {
        this.seatPlayer = testSeatPlayer;
    }

    public String getIp() {
        if (!this.hasPlayer()) {
            return "192.168.3.2";
        }
        return this.seatPlayer.getIp();
    }
}
