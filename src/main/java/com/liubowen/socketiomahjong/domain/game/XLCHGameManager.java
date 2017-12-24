package com.liubowen.socketiomahjong.domain.game;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.liubowen.socketiomahjong.domain.room.RoomContext;
import com.liubowen.socketiomahjong.domain.user.UserContext;
import net.sf.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liubowen
 * @date 2017/11/10 19:06
 * @description
 */
public class XLCHGameManager extends GameManager {

    private RoomContext roomContext;

    private UserContext userContext;


    private static final int gamesIdBase = 0;

    private static final int ACTION_CHUPAI = 1;
    private static final int ACTION_MOPAI = 2;
    private static final int ACTION_PENG = 3;
    private static final int ACTION_GANG = 4;
    private static final int ACTION_HU = 5;
    private static final int ACTION_ZIMO = 6;


    /** 洗牌 */
    public void shuffle(MahjongGame game) {
        List<Card> mahjongs = Lists.newArrayList();
        //筒 (0 ~ 8 表示筒子
        int index = 0;
        for (int i = 0; i < 9; ++i) {
            for (int c = 0; c < 4; ++c) {
                Card card = new Card(MahjongType.TONG, i);
                mahjongs.add(card);
                index++;
            }
        }
        //条 9 ~ 17表示条子
        for (int i = 9; i < 18; ++i) {
            for (int c = 0; c < 4; ++c) {
                Card card = new Card(MahjongType.TIAO, i);
                mahjongs.add(card);
                index++;
            }
        }

        //万 18 ~ 26表示万
        for (int i = 18; i < 27; ++i) {
            for (int c = 0; c < 4; ++c) {
                Card card = new Card(MahjongType.WAN, i);
                mahjongs.add(card);
                index++;
            }
        }
        //  洗牌
        Collections.shuffle(mahjongs);
        game.setMahjongs(mahjongs);
    }

    public int mopai(MahjongGame game, int seatIndex) {
        //  没牌了
        if (game.getCurrentIndex() == game.getMahjongs().size()) {
            return -1;
        }
        GameSeat gameSeat = game.getGameSeat(seatIndex);
        List<Card> mahjongs = gameSeat.getHolds();

        Card pai = game.mopai();
        mahjongs.add(pai);

        return pai.getId();
    }

    /** 发牌 */
    public void deal(MahjongGame game) {
        //强制清0
        game.setCurrentIndex(0);

        //每人13张 一共 13*4 ＝ 52张 庄家多一张 53张
        int seatIndex = game.getButton();
        for (int i = 0; i < 52; ++i) {
            GameSeat gameSeat = game.getGameSeat(seatIndex);
            List<Card> mahjongs = gameSeat.getHolds();
            if (mahjongs == null) {
                mahjongs = Lists.newArrayList();
                gameSeat.setHolds(mahjongs);
            }
            mopai(game, seatIndex);
            seatIndex++;
            seatIndex %= 4;
        }
    }

    //检查是否可以碰
    public void checkCanPeng(MahjongGame game, GameSeat seatData, int targetPai) {
        if (Card.getMJType(targetPai) == seatData.getQue()) {
            return;
        }
        int count = seatData.getCardCount(targetPai);
        if (count >= 2) {
            seatData.setCanPeng(true);
        }
    }

    //检查是否可以点杠
    public void checkCanDianGang(MahjongGame game, GameSeat seatData, int targetPai) {
        //检查玩家手上的牌
        //如果没有牌了，则不能再杠
        if (game.getMahjongs().size() <= game.getCurrentIndex()) {
            return;
        }
        MahjongType mjType = Card.getMJType(targetPai);
        if (mjType == seatData.getQue()) {
            return;
        }
        int count = seatData.getCardCount(targetPai);
        if (count >= 3) {
            seatData.setCanGang(true);
            List<Card> gangPai = seatData.getGangPai();
            gangPai.add(new Card(mjType, targetPai));
            return;
        }
    }

    public void checkCanAnGang(MahjongGame game, GameSeat seatData) {
        //如果没有牌了，则不能再杠
        if (game.getMahjongs().size() <= game.getCurrentIndex()) {
            return;
        }
        List<Card> holds = seatData.getHolds();
        Set<Integer> checkeds = Sets.newHashSet();
        for (Card card : holds) {
            int id = card.getId();
            MahjongType mjType = card.getMahjongType();
            if (mjType == seatData.getQue()) {
                continue;
            }
            if (checkeds.contains(id)) {
                continue;
            }
            checkeds.add(id);
            int count = seatData.getCardCount(id);
            if (count == 4) {
                seatData.setCanGang(true);
                List<Card> gangPai = seatData.getGangPai();
                gangPai.add(new Card(mjType, id));
            }
        }
    }

    //检查是否可以弯杠(自己摸起来的时候)
    public void checkCanWanGang(MahjongGame game, GameSeat seatData) {
        //如果没有牌了，则不能再杠
        if (game.getMahjongs().size() <= game.getCurrentIndex()) {
            return;
        }
        List<Card> pengs = seatData.getPengs();
        Set<Integer> checkeds = Sets.newHashSet();
        //从碰过的牌中选
        for (Card card : pengs) {
            int id = card.getId();
            MahjongType mjType = card.getMahjongType();
            if (mjType == seatData.getQue()) {
                continue;
            }
            if (checkeds.contains(id)) {
                continue;
            }
            int count = seatData.getCardCount(id);
            if (count == 1) {
                seatData.setCanGang(true);
                List<Card> gangPai = seatData.getGangPai();
                gangPai.add(new Card(mjType, id));
            }
        }
    }

    public void checkCanHu(MahjongGame game, GameSeat seatData, int targetPai) {
        game.setLastHuPaiSeat(-1);
        if (Card.getMJType(targetPai) == seatData.getQue()) {
            return;
        }
        seatData.setCanHu(false);
        Map<Integer, TingCard> tingMap = seatData.getTingMap();
        for (TingCard tingCard : tingMap.values()) {
            Card card = tingCard.getCard();
            if (targetPai == card.getId()) {
                seatData.setCanHu(true);
            }
        }
    }

    private void fnClear(GameSeat seatData) {
        seatData.setCanPeng(false);
        seatData.setCanGang(false);
        seatData.setGangPai(Lists.newArrayList());
        seatData.setCanHu(false);
        seatData.setLastFangGangSeat(-1);
    }

    public void clearAllOptions(MahjongGame game, GameSeat seatData) {
        if (seatData != null) {
            fnClear(seatData);
        } else {
            game.setQiangGangContext(null);
            game.getSeats().values().forEach(gameSeat -> {
                fnClear(gameSeat);
            });
        }
    }

    //检查听牌
    public void checkCanTingPai(MahjongGame game, GameSeat seatData) {
        seatData.setTingMap(Maps.newHashMap());
        Map<Integer, TingCard> tingMap = seatData.getTingMap();
        //检查手上的牌是不是已打缺，如果未打缺，则不进行判定
        List<Card> holds = seatData.getHolds();
        for (Card card : holds) {
            if (card.getMahjongType() == seatData.getQue()) {
                return;
            }
        }

        //检查是否是七对 前提是没有碰，也没有杠 ，即手上拥有13张牌
        if (holds.size() == 13) {
            //有5对牌
            boolean hu = false;
            int danPai = -1;
            int pairCount = 0;
            Set<Integer> checkeds = Sets.newHashSet();
            for (Card card : holds) {
                int id = card.getId();
                if (checkeds.contains(id)) {
                    continue;
                }
                checkeds.add(id);
                int count = seatData.getCardCount(id);
                if (count == 2 || count == 3) {
                    pairCount++;
                } else if (count == 4) {
                    pairCount += 2;
                }
                if (count == 1 || count == 3) {
                    //如果已经有单牌了，表示不止一张单牌，并没有下叫。直接闪
                    if (danPai >= 0) {
                        break;
                    }
                    danPai = card.getId();
                }
            }
            //检查是否有6对 并且单牌是不是目标牌
            if (pairCount == 6) {
                //七对只能和一张，就是手上那张单牌
                //七对的番数＝ 2番+N个4个牌（即龙七对）
                TingCard tingCard = new TingCard(new Card(danPai), 2, "7pairs");
                tingMap.put(danPai, tingCard);
                //如果是，则直接返回咯
                return;
            }
        }

        //检查是否是对对胡  由于四川麻将没有吃，所以只需要检查手上的牌
        //对对胡叫牌有两种情况
        //1、N坎 + 1张单牌
        //2、N-1坎 + 两对牌
        int singleCount = 0;
        int colCount = 0;
        int pairCount = 0;
        List<Integer> arr = Lists.newArrayList();

        Map<Integer, Integer> countMap = seatData.getCountMap();
        for (int k : countMap.keySet()) {
            int c = countMap.get(k);
            if (c == 1) {
                singleCount++;
                arr.add(k);
            } else if (c == 2) {
                pairCount++;
                arr.add(k);
            } else if (c == 3) {
                colCount++;
            } else if (c == 4) {
                //手上有4个一样的牌，在四川麻将中是和不了对对胡的 随便加点东西
                singleCount++;
                pairCount += 2;
            }
        }

        if ((pairCount == 2 && singleCount == 0) || (pairCount == 0 && singleCount == 1)) {
            for (int i = 0; i < arr.size(); ++i) {
                //对对胡1番
                int p = arr.get(i);
                if (!tingMap.containsKey(p)) {
                    TingCard tingCard = new TingCard(new Card(p), 1, "duidui");
                    tingMap.put(p, tingCard);
                }
            }
        }
        //检查是不是平胡
        if (seatData.getQue().getType() != 0) {
            MahjongUtils.checkTingPai(seatData, 0, 9);
        }

        if (seatData.getQue().getType() != 1)

        {
            MahjongUtils.checkTingPai(seatData, 9, 18);
        }

        if (seatData.getQue().getType() != 2)

        {
            MahjongUtils.checkTingPai(seatData, 18, 27);
        }
    }

    public int getSeatIndex(long userId) {
        int seatIndex = roomContext.getUserSeat(userId);
        if (seatIndex == -1) {
            return -1;
        }
        return seatIndex;
    }

//    public game getGameByUserID(long userId){
//
//        String roomId = roomContext.getUserRoomId(userId);
//        if(roomId == null){
//            return 0;
//        }
//        var game = games[roomId];
//        return game;
//    }


    public boolean hasOperations(GameSeat seatData) {
        if (seatData.isCanGang() || seatData.isCanPeng() || seatData.isCanHu()) {
            return true;
        }
        return false;
    }

    public void sendOperations(MahjongGame game, GameSeat seatData, int pai) {
        if (hasOperations(seatData)) {
            if (pai == -1) {
                pai = seatData.getHolds().get(seatData.getHolds().size() - 1).getId();
            }
            JSONObject data = new JSONObject();
            data.put("pai", pai);
            data.put("hu", seatData.isCanHu());
            data.put("peng", seatData.isCanPeng());
            data.put("gang", seatData.isCanGang());
            data.put("gangpai", seatData.getGangPais());
            data.put("si", seatData.getIndex());
            //如果可以有操作，则进行操作
            this.userContext.sendMessage(seatData.getUserId(), "game_action_push", data);
        } else {
            this.userContext.sendMessage(seatData.getUserId(), "game_action_push");
        }
    }

    public void moveToNextUser(MahjongGame game, int nextSeat) {
        game.setFangpaoshumu(0);
        //找到下一个没有和牌的玩家
        if (nextSeat < 0) {
            int turn = game.getTurn();
            turn++;
            turn %= 4;
            game.setTurn(turn);
            return;
        } else {
            game.setTurn(nextSeat);
        }
    }
//
//    public void doUserMoPai(game){
//        game.chuPai = -1;
//        var turnSeat = game.gameSeats[game.turn];
//        turnSeat.lastFangGangSeat = -1;
//        turnSeat.guoHuFan = -1;
//        var pai = mopai(game,game.turn);
//        //牌摸完了，结束
//        if(pai == -1){
//            doGameOver(game,turnSeat.userId);
//            return;
//        }
//        else{
//            var numOfMJ = game.mahjongs.length - game.currentIndex;
//            userMgr.broacastInRoom('mj_count_push',numOfMJ,turnSeat.userId,true);
//        }
//
//        recordGameAction(game,game.turn,ACTION_MOPAI,pai);
//
//        //通知前端新摸的牌
//        userMgr.sendMsg(turnSeat.userId,'game_mopai_push',pai);
//        //检查是否可以暗杠或者胡
//        //检查胡，直杠，弯杠
//        if(!turnSeat.hued){
//            checkCanAnGang(game,turnSeat);
//        }
//
//        //如果未胡牌，或者摸起来的牌可以杠，才检查弯杠
//        if(!turnSeat.hued || turnSeat.holds[turnSeat.holds.length-1] == pai){
//            checkCanWanGang(game,turnSeat,pai);
//        }
//
//
//        //检查看是否可以和
//        checkCanHu(game,turnSeat,pai);
//
//        //广播通知玩家出牌方
//        turnSeat.canChuPai = true;
//        userMgr.broacastInRoom('game_chupai_push',turnSeat.userId,turnSeat.userId,true);
//
//        //通知玩家做对应操作
//        sendOperations(game,turnSeat,game.chuPai);
//    }

    public boolean isSameType(int type, List<Integer> arr) {
        for (int i = 0; i < arr.size(); ++i) {
            int t = Card.getMJType(arr.get(i)).getType();
            if (type != -1 && type != t) {
                return false;
            }
            type = t;
        }
        return true;
    }
//
//    public void isQingYiSe(gameSeatData){
//        var type = getMJType(gameSeatData.holds[0]);
//
//        //检查手上的牌
//        if(isSameType(type,gameSeatData.holds) == false){
//            return false;
//        }
//
//        //检查杠下的牌
//        if(isSameType(type,gameSeatData.angangs) == false){
//            return false;
//        }
//        if(isSameType(type,gameSeatData.wangangs) == false){
//            return false;
//        }
//        if(isSameType(type,gameSeatData.diangangs) == false){
//            return false;
//        }
//
//        //检查碰牌
//        if(isSameType(type,gameSeatData.pengs) == false){
//            return false;
//        }
//        return true;
//    }

    public boolean isMenQing(GameSeat gameSeatData) {
        return (gameSeatData.getPengs().size() + gameSeatData.getWangangs().size() + gameSeatData.getDiangangs().size()) == 0;
    }
//
//    public void isZhongZhang(gameSeatData){
//        var fn = public void(arr){
//            for(var i = 0; i < arr.length; ++i){
//                var pai = arr[i];
//                if(pai == 0 || pai == 8 || pai == 9 || pai == 17 || pai == 18 || pai == 26){
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        if(fn(gameSeatData.pengs) == false){
//            return false;
//        }
//        if(fn(gameSeatData.angangs) == false){
//            return false;
//        }
//        if(fn(gameSeatData.diangangs) == false){
//            return false;
//        }
//        if(fn(gameSeatData.wangangs) == false){
//            return false;
//        }
//        if(fn(gameSeatData.holds) == false){
//            return false;
//        }
//        return true;
//    }
//
//    public void isJiangDui(gameSeatData){
//        var fn = public void(arr){
//            for(var i = 0; i < arr.length; ++i){
//                var pai = arr[i];
//                if(pai != 1 && pai != 4 && pai != 7
//                        && pai != 9 && pai != 13 && pai != 16
//                        && pai != 18 && pai != 21 && pai != 25
//                        ){
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        if(fn(gameSeatData.pengs) == false){
//            return false;
//        }
//        if(fn(gameSeatData.angangs) == false){
//            return false;
//        }
//        if(fn(gameSeatData.diangangs) == false){
//            return false;
//        }
//        if(fn(gameSeatData.wangangs) == false){
//            return false;
//        }
//        if(fn(gameSeatData.holds) == false){
//            return false;
//        }
//        return true;
//    }
//
//    public void isTinged(seatData){
//        for(var k in seatData.tingMap){
//            return true;
//        }
//        return false;
//    }
//
//    public void computeFanScore(game,fan){
//        if(fan > game.conf.maxFan){
//            fan = game.conf.maxFan;
//        }
//        return (1 << fan) * game.conf.baseScore;
//    }
//
//    //是否需要查大叫(有人没有下叫)
//    public void needChaDaJiao(game){
//        //查叫
//        var numOfHued = 0;
//        var numOfTinged = 0;
//        var numOfUntinged = 0;
//        for(var i = 0; i < game.gameSeats.length; ++i){
//            var ts = game.gameSeats[i];
//            if(ts.hued){
//                numOfHued ++;
//                numOfTinged++;
//            }
//            else if(isTinged(ts)){
//                numOfTinged++;
//            }
//            else{
//                numOfUntinged++;
//            }
//        }
//
//        //如果没有任何一个人叫牌，则不需要查叫
//        if(numOfTinged == 0){
//            return false;
//        }
//
//        //如果都听牌了，也不需要查叫
//        if(numOfUntinged == 0){
//            return false;
//        }
//        return true;
//    }
//
//    public void findMaxFanTingPai(ts){
//        //找出最大番
//        var cur = null;
//        for(var k in ts.tingMap){
//            var tpai = ts.tingMap[k];
//            if(cur == null || tpai.fan > cur.fan){
//                cur = tpai;
//                cur.pai = parseInt(k);
//            }
//        }
//        return cur;
//    }
//
//    public void findUnTingedPlayers(game){
//        var arr = [];
//        for(var i = 0; i < game.gameSeats.length; ++i){
//            var ts = game.gameSeats[i];
//            //如果没有胡，且没有听牌
//            if(!ts.hued && !isTinged(ts)){
//                arr.push(i);
//            }
//        }
//        return arr;
//    }
//
//    public void getNumOfGen(seatData){
//        var numOfGangs = seatData.diangangs.length + seatData.wangangs.length + seatData.angangs.length;
//        for(var k = 0; k < seatData.pengs.length; ++k){
//            var pai = seatData.pengs[k];
//            if(seatData.countMap[pai] == 1){
//                numOfGangs++;
//            }
//        }
//        for(var k in seatData.countMap){
//            if(seatData.countMap[k] == 4){
//                numOfGangs++;
//            }
//        }
//        return numOfGangs;
//    }
//
//    public void chaJiao(game){
//        var arr = findUnTingedPlayers(game);
//        if(arr.length == 0){
//            return;
//        }
//        for(var i = 0; i < game.gameSeats.length; ++i){
//            var ts = game.gameSeats[i];
//            //如果听牌了，则未叫牌的人要给钱
//            if(isTinged(ts)){
//                var cur = findMaxFanTingPai(ts);
//                ts.huInfo.push({
//                        ishupai:true,
//                        action:"chadajiao",
//                        fan:cur.fan,
//                        pattern:cur.pattern,
//                        pai:cur.pai,
//                        numofgen:getNumOfGen(ts),
//            });
//
//                for(var j = 0; j < arr.length; ++j){
//                    game.gameSeats[arr[j]].huInfo.push({
//                            action:"beichadajiao",
//                            target:i,
//                            index:ts.huInfo.length-1,
//                });
//                }
//            }
//        }
//    }
//
//    public void calculateResult(game,roomInfo){
//
//        var isNeedChaDaJia = needChaDaJiao(game);
//        if(isNeedChaDaJia){
//            chaJiao(game);
//        }
//
//        var baseScore = game.conf.baseScore;
//
//        for(var i = 0; i < game.gameSeats.length; ++i){
//            var sd = game.gameSeats[i];
//            //对所有胡牌的玩家进行统计
//            if(isTinged(sd)){
//                //收杠钱
//                var additonalscore = 0;
//                for(var a = 0; a < sd.actions.length; ++a){
//                    var ac = sd.actions[a];
//                    if(ac.type == "fanggang"){
//                        var ts = game.gameSeats[ac.targets[0]];
//                        //检查放杠的情况，如果目标没有和牌，且没有叫牌，则不算 用于优化前端显示
//                        if(isNeedChaDaJia && (ts.hued) == false && (isTinged(ts) == false)){
//                            ac.state = "nop";
//                        }
//                    }
//                    else if(ac.type == "angang" || ac.type == "wangang" || ac.type == "diangang"){
//                        if(ac.state != "nop"){
//                            var acscore = ac.score;
//                            additonalscore += ac.targets.length * acscore * baseScore;
//                            //扣掉目标方的分
//                            for(var t = 0; t < ac.targets.length; ++t){
//                                var six = ac.targets[t];
//                                game.gameSeats[six].score -= acscore * baseScore;
//                            }
//                        }
//                    }
//                    else if(ac.type == "maozhuanyu"){
//                        //对于呼叫转移，如果对方没有叫牌，表示不得行
//                        if(isTinged(ac.owner)){
//                            //如果
//                            var ref = ac.ref;
//                            var acscore = ref.score;
//                            var total = ref.targets.length * acscore * baseScore;
//                            additonalscore += total;
//                            //扣掉目标方的分
//                            if(ref.payTimes == 0){
//                                for(var t = 0; t < ref.targets.length; ++t){
//                                    var six = ref.targets[t];
//                                    game.gameSeats[six].score -= acscore * baseScore;
//                                }
//                            }
//                            else{
//                                //如果已经被扣过一次了，则由杠牌这家赔
//                                ac.owner.score -= total;
//                            }
//                            ref.payTimes++;
//                            ac.owner = null;
//                            ac.ref = null;
//                        }
//                    }
//                }
//
//                if(isQingYiSe(sd)){
//                    sd.qingyise = true;
//                }
//
//                if(game.conf.menqing){
//                    sd.isMenQing = isMenQing(sd);
//                }
//
//                //金钩胡
//                if(sd.holds.length == 1 || sd.holds.length == 2){
//                    sd.isJinGouHu = true;
//                }
//
//                sd.numAnGang = sd.angangs.length;
//                sd.numMingGang = sd.wangangs.length + sd.diangangs.length;
//
//                //进行胡牌结算
//                for(var j = 0; j < sd.huInfo.length; ++j){
//                    var info = sd.huInfo[j];
//                    if(!info.ishupai){
//                        sd.numDianPao++;
//                        continue;
//                    }
//                    //统计自己的番子和分数
//                    //基础番(平胡0番，对对胡1番、七对2番) + 清一色2番 + 杠+1番
//                    //杠上花+1番，杠上炮+1番 抢杠胡+1番，金钩胡+1番，海底胡+1番
//                    var fan = info.fan;
//                    sd.holds.push(info.pai);
//                    if(sd.countMap[info.pai] != null){
//                        sd.countMap[info.pai] ++;
//                    }
//                    else{
//                        sd.countMap[info.pai] = 1;
//                    }
//
//                    if(sd.qingyise){
//                        fan += 2;
//                    }
//
//                    //金钩胡
//                    if(sd.isJinGouHu){
//                        fan += 1;
//                    }
//
//                    if(info.isHaiDiHu){
//                        fan += 1;
//                    }
//
//                    if(game.conf.tiandihu){
//                        if(info.isTianHu){
//                            fan += 3;
//                        }
//                        else if(info.isDiHu){
//                            fan += 2;
//                        }
//                    }
//
//                    var isjiangdui = false;
//                    if(game.conf.jiangdui){
//                        if(info.pattern == "7pairs"){
//                            if(info.numofgen > 0){
//                                info.numofgen -= 1;
//                                info.pattern == "l7pairs";
//                                isjiangdui = isJiangDui(sd);
//                                if(isjiangdui){
//                                    info.pattern == "j7paris";
//                                    fan += 2;
//                                }
//                                else{
//                                    fan += 1;
//                                }
//                            }
//                        }
//                        else if(info.pattern == "duidui"){
//                            isjiangdui = isJiangDui(sd);
//                            if(isjiangdui){
//                                info.pattern = "jiangdui";
//                                fan += 2;
//                            }
//                        }
//                    }
//
//                    if(game.conf.menqing){
//                        //不是将对，才检查中张
//                        if(!isjiangdui){
//                            sd.isZhongZhang = isZhongZhang(sd);
//                            if(sd.isZhongZhang){
//                                fan += 1;
//                            }
//                        }
//
//                        if(sd.isMenQing){
//                            fan += 1;
//                        }
//                    }
//
//                    fan += info.numofgen;
//
//                    if(info.action == "ganghua" || info.action == "dianganghua" || info.action == "gangpaohu" || info.action == "qiangganghu"){
//                        fan += 1;
//                    }
//
//                    var extraScore = 0;
//                    if(info.iszimo){
//                        if(game.conf.zimo == 0){
//                            //自摸加底
//                            extraScore = baseScore;
//                        }
//                        else if(game.conf.zimo == 1){
//                            fan += 1;
//                        }
//                        else{
//                            //nothing.
//                        }
//                    }
//                    //和牌的玩家才加这个分
//                    var score = computeFanScore(game,fan) + extraScore;
//                    if(info.action == "chadajiao"){
//                        //收所有没有叫牌的人的钱
//                        for(var t = 0; t < game.gameSeats.length; ++t){
//                            if(!isTinged(game.gameSeats[t])){
//                                game.gameSeats[t].score -= score;
//                                sd.score += score;
//                                //被查叫次数
//                                if(game.gameSeats[t] != sd){
//                                    game.gameSeats[t].numChaJiao++;
//                                }
//                            }
//                        }
//                    }
//                    else if(info.iszimo){
//                        //收所有人的钱
//                        sd.score += score * game.gameSeats.length;
//                        for(var t = 0; t < game.gameSeats.length; ++t){
//                            game.gameSeats[t].score -= score;
//                        }
//                        sd.numZiMo++;
//                    }
//                    else{
//                        //收放炮者的钱
//                        sd.score += score;
//                        game.gameSeats[info.target].score -= score;
//                        sd.numJiePao++;
//                    }
//
//                    //撤除胡的那张牌
//                    sd.holds.pop();
//                    sd.countMap[info.pai]--;
//
//                    if(fan > game.conf.maxFan){
//                        fan = game.conf.maxFan;
//                    }
//                    info.fan = fan;
//                }
//                //一定要用 += 。 因为此时的sd.score可能是负的
//                sd.score += additonalscore;
//            }
//            else{
//                for(var a = sd.actions.length -1; a >= 0; --a){
//                    var ac = sd.actions[a];
//                    if(ac.type == "angang" || ac.type == "wangang" || ac.type == "diangang"){
//                        //如果3家都胡牌，则需要结算。否则认为是查叫
//                        if(isNeedChaDaJia){
//                            sd.actions.splice(a,1);
//                        }
//                        else{
//                            if(ac.state != "nop"){
//                                var acscore = ac.score;
//                                sd.score += ac.targets.length * acscore * baseScore;
//                                //扣掉目标方的分
//                                for(var t = 0; t < ac.targets.length; ++t){
//                                    var six = ac.targets[t];
//                                    game.gameSeats[six].score -= acscore * baseScore;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public void doGameOver(game,userId,forceEnd){
//        var roomId = roomMgr.getUserRoom(userId);
//        if(roomId == null){
//            return;
//        }
//        var roomInfo = roomMgr.getRoom(roomId);
//        if(roomInfo == null){
//            return;
//        }
//
//        var results = [];
//        var dbresult = [0,0,0,0];
//
//        var fnNoticeResult = public void(isEnd){
//            var endinfo = null;
//            if(isEnd){
//                endinfo = [];
//                for(var i = 0; i < roomInfo.seats.length; ++i){
//                    var rs = roomInfo.seats[i];
//                    endinfo.push({
//                            numzimo:rs.numZiMo,
//                            numjiepao:rs.numJiePao,
//                            numdianpao:rs.numDianPao,
//                            numangang:rs.numAnGang,
//                            numminggang:rs.numMingGang,
//                            numchadajiao:rs.numChaJiao,
//                });
//                }
//            }
//
//            userMgr.broacastInRoom('game_over_push',{results:results,endinfo:endinfo},userId,true);
//            //如果局数已够，则进行整体结算，并关闭房间
//            if(isEnd){
//                setTimeout(public void(){
//                    if(roomInfo.numOfGames > 1){
//                        store_history(roomInfo);
//                    }
//                    userMgr.kickAllInRoom(roomId);
//                    roomMgr.destroy(roomId);
//                    db.archive_games(roomInfo.uuid);
//                },1500);
//            }
//        }
//
//        if(game != null){
//            if(!forceEnd){
//                calculateResult(game,roomInfo);
//            }
//
//            for(var i = 0; i < roomInfo.seats.length; ++i){
//                var rs = roomInfo.seats[i];
//                var sd = game.gameSeats[i];
//
//                rs.ready = false;
//                rs.score += sd.score
//                rs.numZiMo += sd.numZiMo;
//                rs.numJiePao += sd.numJiePao;
//                rs.numDianPao += sd.numDianPao;
//                rs.numAnGang += sd.numAnGang;
//                rs.numMingGang += sd.numMingGang;
//                rs.numChaJiao += sd.numChaJiao;
//
//                var userRT = {
//                        userId:sd.userId,
//                        actions:[],
//                pengs:sd.pengs,
//                        wangangs:sd.wangangs,
//                        diangangs:sd.diangangs,
//                        angangs:sd.angangs,
//                        holds:sd.holds,
//                        score:sd.score,
//                        totalscore:rs.score,
//                        qingyise:sd.qingyise,
//                        menqing:sd.isMenQing,
//                        jingouhu:sd.isJinGouHu,
//                        huinfo:sd.huInfo,
//            }
//
//                for(var k in sd.actions){
//                    userRT.actions[k] = {
//                            type:sd.actions[k].type,
//                };
//                }
//                results.push(userRT);
//
//
//                dbresult[i] = sd.score;
//                delete gameSeatsOfUsers[sd.userId];
//            }
//            delete games[roomId];
//
//            var old = roomInfo.nextButton;
//            if(game.yipaoduoxiang >= 0){
//                roomInfo.nextButton = game.yipaoduoxiang;
//            }
//            else if(game.firstHupai >= 0){
//                roomInfo.nextButton = game.firstHupai;
//            }
//            else{
//                roomInfo.nextButton = (game.turn + 1) % 4;
//            }
//
//            if(old != roomInfo.nextButton){
//                db.update_next_button(roomId,roomInfo.nextButton);
//            }
//        }
//
//        if(forceEnd || game == null){
//            fnNoticeResult(true);
//        }
//        else{
//            //保存游戏
//            store_game(game,public void(ret){
//                db.update_game_result(roomInfo.uuid,game.gameIndex,dbresult);
//
//                //记录玩家操作
//                var str = JSON.stringify(game.actionList);
//                db.update_game_action_records(roomInfo.uuid,game.gameIndex,str);
//
//                //保存游戏局数
//                db.update_num_of_turns(roomId,roomInfo.numOfGames);
//
//                //如果是第一次，则扣除房卡
//                if(roomInfo.numOfGames == 1){
//                    var cost = 2;
//                    if(roomInfo.conf.maxGames == 8){
//                        cost = 3;
//                    }
//                    db.cost_gems(game.gameSeats[0].userId,cost);
//                }
//
//                var isEnd = (roomInfo.numOfGames >= roomInfo.conf.maxGames);
//                fnNoticeResult(isEnd);
//            });
//        }
//    }
//
//    public void recordUserAction(MahjongGame game, GameSeat seatData,type,target){
//        var d = {type:type,targets:[]};
//        if(target != null){
//            if(typeof(target) == 'number'){
//                d.targets.push(target);
//            }
//            else{
//                d.targets = target;
//            }
//        }
//        else{
//            for(var i = 0; i < game.gameSeats.length; ++i){
//                var s = game.gameSeats[i];
//                //血流成河，所有自摸，暗杠，弯杠，都算三家
//                if(i != seatData.seatIndex/* && s.hued == false*/){
//                    d.targets.push(i);
//                }
//            }
//        }
//
//        seatData.actions.push(d);
//        return d;
//    }
//
//    public void recordGameAction(game,si,action,pai){
//        game.actionList.push(si);
//        game.actionList.push(action);
//        if(pai != null){
//            game.actionList.push(pai);
//        }
//    }
//
//    exports.setReady = public void(userId,callback){
//        var roomId = roomMgr.getUserRoom(userId);
//        if(roomId == null){
//            return;
//        }
//        var roomInfo = roomMgr.getRoom(roomId);
//        if(roomInfo == null){
//            return;
//        }
//
//        roomMgr.setReady(userId,true);
//
//        var game = games[roomId];
//        if(game == null){
//            if(roomInfo.seats.length == 4){
//                for(var i = 0; i < roomInfo.seats.length; ++i){
//                    var s = roomInfo.seats[i];
//                    if(s.ready == false || userMgr.isOnline(s.userId)==false){
//                        return;
//                    }
//                }
//                //4个人到齐了，并且都准备好了，则开始新的一局
//                exports.begin(roomId);
//            }
//        }
//        else{
//            var numOfMJ = game.mahjongs.length - game.currentIndex;
//            var remainingGames = roomInfo.conf.maxGames - roomInfo.numOfGames;
//
//            var data = {
//                    state:game.state,
//                    numofmj:numOfMJ,
//                    button:game.button,
//                    turn:game.turn,
//                    chuPai:game.chuPai,
//        };
//
//            data.seats = [];
//            var seatData = null;
//            for(var i = 0; i < 4; ++i){
//                var sd = game.gameSeats[i];
//
//                var s = {
//                        userid:sd.userId,
//                        folds:sd.folds,
//                        angangs:sd.angangs,
//                        diangangs:sd.diangangs,
//                        wangangs:sd.wangangs,
//                        pengs:sd.pengs,
//                        que:sd.que,
//                        hued:sd.hued,
//                        huinfo:sd.huInfo,
//                        iszimo:sd.iszimo,
//            }
//                if(sd.userId == userId){
//                    s.holds = sd.holds;
//                    s.huanpais = sd.huanpais;
//                    seatData = sd;
//                }
//                else{
//                    s.huanpais = sd.huanpais? []:null;
//                }
//                data.seats.push(s);
//            }
//
//            //同步整个信息给客户端
//            userMgr.sendMsg(userId,'game_sync_push',data);
//            sendOperations(MahjongGame game, GameSeat seatData,game.chuPai);
//        }
//    }
//
//    public void store_single_history(userId,history){
//        db.get_user_history(userId,public void(data){
//            if(data == null){
//                data = [];
//            }
//            while(data.length >= 10){
//                data.shift();
//            }
//            data.push(history);
//            db.update_user_history(userId,data);
//        });
//    }
//
//    public void store_history(roomInfo){
//        var seats = roomInfo.seats;
//        var history = {
//                uuid:roomInfo.uuid,
//                id:roomInfo.id,
//                time:roomInfo.createTime,
//                seats:new Array(4)
//    };
//
//        for(var i = 0; i < seats.length; ++i){
//            var rs = seats[i];
//            var hs = history.seats[i] = {};
//            hs.userid = rs.userId;
//            hs.name = crypto.toBase64(rs.name);
//            hs.score = rs.score;
//        }
//
//        for(var i = 0; i < seats.length; ++i){
//            var s = seats[i];
//            store_single_history(s.userId,history);
//        }
//    }
//
//
//    public void construct_game_base_info(game){
//        var baseInfo = {
//                type:game.conf.type,
//                button:game.button,
//                index:game.gameIndex,
//                mahjongs:game.mahjongs,
//                game_seats:new Array(4)
//    }
//        for(var i = 0; i < 4; ++i){
//            baseInfo.game_seats[i] = game.gameSeats[i].holds;
//        }
//        game.baseInfoJson = JSON.stringify(baseInfo);
//    }
//
//    public void store_game(game,callback){
//        db.create_game(game.roomInfo.uuid,game.gameIndex,game.baseInfoJson,callback);
//    }
//
////开始新的一局
//    exports.begin = public void(roomId) {
//        var roomInfo = roomMgr.getRoom(roomId);
//        if(roomInfo == null){
//            return;
//        }
//        var seats = roomInfo.seats;
//
//        var game = {
//                conf:roomInfo.conf,
//                roomInfo:roomInfo,
//                gameIndex:roomInfo.numOfGames,
//
//                button:roomInfo.nextButton,
//                mahjongs:new Array(108),
//                currentIndex:0,
//                gameSeats:new Array(4),
//
//                numOfQue:0,
//                turn:0,
//                chuPai:-1,
//                state:"idle",
//                firstHupai:-1,
//                yipaoduoxiang:-1,
//                fangpaoshumu:-1,
//                actionList:[],
//        chupaiCnt:0,
//    };
//
//        roomInfo.numOfGames++;
//
//        for(var i = 0; i < 4; ++i){
//            var data = game.gameSeats[i] = {};
//
//            data.game = game;
//
//            data.seatIndex = i;
//
//            data.userId = seats[i].userId;
//            //持有的牌
//            data.holds = [];
//            //打出的牌
//            data.folds = [];
//            //暗杠的牌
//            data.angangs = [];
//            //点杠的牌
//            data.diangangs = [];
//            //弯杠的牌
//            data.wangangs = [];
//            //碰了的牌
//            data.pengs = [];
//            //缺一门
//            data.que = -1;
//
//            //换三张的牌
//            data.huanpais = null;
//
//            //玩家手上的牌的数目，用于快速判定碰杠
//            data.countMap = {};
//            //玩家听牌，用于快速判定胡了的番数
//            data.tingMap = {};
//            data.pattern = "";
//
//            //是否可以杠
//            data.canGang = false;
//            //用于记录玩家可以杠的牌
//            data.gangPai = [];
//
//            //是否可以碰
//            data.canPeng = false;
//            //是否可以胡
//            data.canHu = false;
//            //是否可以出牌
//            data.canChuPai = false;
//
//            //如果guoHuFan >=0 表示处于过胡状态，
//            //如果过胡状态，那么只能胡大于过胡番数的牌
//            data.guoHuFan = -1;
//
//            //是否胡了
//            data.hued = false;
//            //
//            data.actions = [];
//
//            //是否是自摸
//            data.iszimo = false;
//            data.isGangHu = false;
//            data.fan = 0;
//            data.score = 0;
//            data.huInfo = [];
//
//
//            data.lastFangGangSeat = -1;
//
//            //统计信息
//            data.numZiMo = 0;
//            data.numJiePao = 0;
//            data.numDianPao = 0;
//            data.numAnGang = 0;
//            data.numMingGang = 0;
//            data.numChaJiao = 0;
//
//            gameSeatsOfUsers[data.userId] = data;
//        }
//        games[roomId] = game;
//        //洗牌
//        shuffle(game);
//        //发牌
//        deal(game);
//
//
//
//        var numOfMJ = game.mahjongs.length - game.currentIndex;
//        var huansanzhang = roomInfo.conf.hsz;
//
//        for(var i = 0; i < seats.length; ++i){
//            //开局时，通知前端必要的数据
//            var s = seats[i];
//            //通知玩家手牌
//            userMgr.sendMsg(s.userId,'game_holds_push',game.gameSeats[i].holds);
//            //通知还剩多少张牌
//            userMgr.sendMsg(s.userId,'mj_count_push',numOfMJ);
//            //通知还剩多少局
//            userMgr.sendMsg(s.userId,'game_num_push',roomInfo.numOfGames);
//            //通知游戏开始
//            userMgr.sendMsg(s.userId,'game_begin_push',game.button);
//
//            if(huansanzhang == true){
//                game.state = "huanpai";
//                //通知准备换牌
//                userMgr.sendMsg(s.userId,'game_huanpai_push');
//            }
//            else{
//                game.state = "dingque";
//                //通知准备定缺
//                userMgr.sendMsg(s.userId,'game_dingque_push');
//            }
//        }
//    }
//

    @Override
    public void doDissolve() {

    }

    @Override
    public void setReady(long userId) {

    }

    @Override
    public void chuPai(long userId, String pai) {

    }

    @Override
    public void dingQue(long userId, String que) {

    }

    @Override
    public void huanSanZhang(long userId, String p1, String p2, String p3) {

    }

    @Override
    public void peng(long userId) {

    }

    @Override
    public void gang(long userId, String pai) {

    }

    @Override
    public void hu(long userId) {

    }

    @Override
    public void guo(long userId) {

    }

    @Override
    public boolean hasBegan() {
        return false;
    }

    @Override
    public JSONObject dissolveRequest(long userId) {
        return null;
    }

    @Override
    public JSONObject dissolveAgree(long userId, boolean b) {
        return null;
    }
}
