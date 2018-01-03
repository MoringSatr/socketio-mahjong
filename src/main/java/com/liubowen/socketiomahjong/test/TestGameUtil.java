package com.liubowen.socketiomahjong.test;

import com.liubowen.socketiomahjong.domain.game.MahjongType;

/**
 * @author liubowen
 * @date 2017/12/29 1:49
 * @description
 */
public class TestGameUtil {

    /** 根据牌的id判断牌是什么类型 */
    public static MahjongType getMJType(byte pai) {
        if (pai >= 0 && pai < 9) {
            //筒
            return MahjongType.TONG;
        } else if (pai >= 9 && pai < 18) {
            //条
            return MahjongType.TIAO;
        } else if (pai >= 18 && pai < 27) {
            //万
            return MahjongType.WAN;
        }
        return MahjongType.FENG;
    }

    /** 判断玩家是否可以碰牌 */

    /** 判断玩家是否可以暗杠 */

    /** 判断玩家是否可以明杠 */

    /** 判断玩家是否可以弯杠 */

    /** 判断玩家是否可以胡牌 */

    /** 判断玩家是否可以吃牌 */

    /**  */

    public static void main(String[] args) throws Exception {
//        List<Integer> integers = Lists.newArrayList();
//        integers.add(1);
//        integers.add(2);
//        integers.add(2);
//        integers.add(3);
//        integers.add(5);
//        integers.add(7);
//        integers.add(8);
//        integers.add(11);
//
//        System.err.println(Arrays.toString(integers.toArray()));
//        integers.remove((Integer) 11);
//        integers.remove((Integer) 2);
//        integers.remove((Integer) 2);
//        integers.remove((Integer) 5);
//        System.err.println(Arrays.toString(integers.toArray()));

        for (int i = 0; i < 3; i++) {
            System.err.println(i);
        }
    }
}
