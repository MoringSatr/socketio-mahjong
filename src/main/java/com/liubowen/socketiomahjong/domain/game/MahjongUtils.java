package com.liubowen.socketiomahjong.domain.game;

import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/24 16:20
 * @description
 */
public class MahjongUtils {

    public static void checkTingPai(GameSeat seatData, int begin, int end) {

        for (int i = begin; i < end; ++i) {
            //如果这牌已经在和了，就不用检查了
            Map<Integer, TingCard> tingMap = seatData.getTingMap();
            if (tingMap.containsKey(i)) {
                continue;
            }
            //将牌加入到计数中
            Map<Integer, Integer> countMap = seatData.getCountMap();
            int old = 0;
            if (!countMap.containsKey(i)) {
                old = 0;
                countMap.put(i, 1);
            } else {
                countMap.put(i, countMap.get(i) + 1);
            }
            seatData.getHolds().add(new Card(i));
            //逐个判定手上的牌
            boolean ret = checkCanHu(seatData);
            if (ret) {
                //平胡 0番
                TingCard tingCard = new TingCard(new Card(i), 0, "normal");
                tingMap.put(i, tingCard);
            }

            //搞完以后，撤消刚刚加的牌
            countMap.put(countMap.get(i), old);
            if (seatData.getHolds().size() > 0) {
                seatData.getHolds().remove(seatData.getHolds().size() - 1);
            }

        }
    }

    public static boolean checkCanHu(GameSeat seatData) {
        Map<Integer, Integer> countMap = seatData.getCountMap();
        for (int k : countMap.keySet()) {
            int c = countMap.get(k);
            if (c < 2) {
                continue;
            }
            //如果当前牌大于等于２，则将它选为将牌
            countMap.put(k, countMap.get(k) - 2);
            //逐个判定剩下的牌是否满足　３Ｎ规则,一个牌会有以下几种情况
            //1、0张，则不做任何处理
            //2、2张，则只可能是与其它牌形成匹配关系
            //3、3张，则可能是单张形成 A-2,A-1,A  A-1,A,A+1  A,A+1,A+2，也可能是直接成为一坎
            //4、4张，则只可能是一坎+单张
            boolean ret = checkSingle(seatData);
            countMap.put(k, countMap.get(k) + 2);
            if (ret) {
                return true;
            }
        }
        return false;
    }


    public static boolean checkSingle(GameSeat seatData) {
        List<Card> holds = seatData.getHolds();
        Map<Integer, Integer> countMap = seatData.getCountMap();
        int selected = -1;
        int c = 0;
        for (int i = 0; i < holds.size(); ++i) {
            int pai = holds.get(i).getId();
            c = countMap.get(pai);
            if (c != 0) {
                selected = pai;
                break;
            }
        }
        //如果没有找到剩余牌，则表示匹配成功了
        if (selected == -1) {
            return true;
        }
        //否则，进行匹配
        if (c == 3) {
            //直接作为一坎
            countMap.put(selected, 0);
//            debugRecord(selected);
//            debugRecord(selected);
//            debugRecord(selected);
            boolean ret = checkSingle(seatData);
            //立即恢复对数据的修改
            countMap.put(selected, c);
            if (ret == true) {
                return true;
            }
        } else if (c == 4) {
            //直接作为一坎
            countMap.put(selected, 1);
//            debugRecord(selected);
//            debugRecord(selected);
//            debugRecord(selected);
            boolean ret = checkSingle(seatData);
            //立即恢复对数据的修改
            countMap.put(selected, c);
            //如果作为一坎能够把牌匹配完，直接返回TRUE。
            if (ret == true) {
                return true;
            }
        }

        //按单牌处理
        return matchSingle(seatData, selected);
    }

    public static boolean matchSingle(GameSeat seatData, int selected) {
        Map<Integer, Integer> countMap = seatData.getCountMap();
        //分开匹配 A-2,A-1,A
        boolean matched = true;
        int v = selected % 9;
        if (v < 2) {
            matched = false;
        } else {
            for (int i = 0; i < 3; ++i) {
                int t = selected - 2 + i;
                int cc = countMap.get(t);
                if (!countMap.containsKey(cc)) {
                    matched = false;
                    break;
                }
                if (cc == 0) {
                    matched = false;
                    break;
                }
            }
        }


        //匹配成功，扣除相应数值
        if (matched) {
            countMap.put(selected - 2, countMap.get(selected - 2) - 1);
            countMap.put(selected - 1, countMap.get(selected - 1) - 1);
            countMap.put(selected, countMap.get(selected) - 1);
            boolean ret = checkSingle(seatData);
            countMap.put(selected - 2, countMap.get(selected - 2) + 1);
            countMap.put(selected - 1, countMap.get(selected - 1) + 1);
            countMap.put(selected, countMap.get(selected) + 1);
            if (ret == true) {
//                debugRecord(selected - 2);
//                debugRecord(selected - 1);
//                debugRecord(selected);
                return true;
            }
        }

        //分开匹配 A-1,A,A + 1
        matched = true;
        if (v < 1 || v > 7) {
            matched = false;
        } else {
            for (int i = 0; i < 3; ++i) {
                int t = selected - 1 + i;
                int cc = countMap.get(t);
                if (!countMap.containsKey(cc)) {
                    matched = false;
                    break;
                }
                if (cc == 0) {
                    matched = false;
                    break;
                }
            }
        }

        //匹配成功，扣除相应数值
        if (matched) {
            countMap.put(selected - 1, countMap.get(selected - 1) - 1);
            countMap.put(selected, countMap.get(selected) - 1);
            countMap.put(selected + 1, countMap.get(selected + 1) - 1);

            boolean ret = checkSingle(seatData);
            countMap.put(selected - 1, countMap.get(selected - 1) + 1);
            countMap.put(selected, countMap.get(selected) + 1);
            countMap.put(selected + 1, countMap.get(selected + 1) + 1);
            if (ret == true) {
//                debugRecord(selected - 1);
//                debugRecord(selected);
//                debugRecord(selected + 1);
                return true;
            }
        }


        //分开匹配 A,A+1,A + 2
        matched = true;
        if (v > 6) {
            matched = false;
        } else {
            for (int i = 0; i < 3; ++i) {
                int t = selected + i;
                int cc = countMap.get(t);
                if (!countMap.containsKey(cc)) {
                    matched = false;
                    break;
                }
                if (cc == 0) {
                    matched = false;
                    break;
                }
            }
        }

        //匹配成功，扣除相应数值
        if (matched) {
            countMap.put(selected, countMap.get(selected) - 1);
            countMap.put(selected + 1, countMap.get(selected + 1) - 1);
            countMap.put(selected + 2, countMap.get(selected + 2) - 1);
            boolean ret = checkSingle(seatData);
            countMap.put(selected, countMap.get(selected) + 1);
            countMap.put(selected + 1, countMap.get(selected + 1) + 1);
            countMap.put(selected + 2, countMap.get(selected + 2) + 1);
            if (ret == true) {
//                debugRecord(selected);
//                debugRecord(selected + 1);
//                debugRecord(selected + 2);
                return true;
            }
        }
        return false;
    }

}
