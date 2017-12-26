package com.liubowen.socketiomahjong.domain.game;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.liubowen.socketiomahjong.domain.room.Room;
import com.liubowen.socketiomahjong.domain.room.RoomContext;
import com.liubowen.socketiomahjong.domain.room.Seat;
import com.liubowen.socketiomahjong.domain.user.UserContext;
import lombok.experimental.var;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        // 筒 (0 ~ 8 表示筒子
        int index = 0;
        for (int i = 0; i < 9; ++i) {
            for (int c = 0; c < 4; ++c) {
                Card card = new Card(MahjongType.TONG, i);
                mahjongs.add(card);
                index++;
            }
        }
        // 条 9 ~ 17表示条子
        for (int i = 9; i < 18; ++i) {
            for (int c = 0; c < 4; ++c) {
                Card card = new Card(MahjongType.TIAO, i);
                mahjongs.add(card);
                index++;
            }
        }

        // 万 18 ~ 26表示万
        for (int i = 18; i < 27; ++i) {
            for (int c = 0; c < 4; ++c) {
                Card card = new Card(MahjongType.WAN, i);
                mahjongs.add(card);
                index++;
            }
        }
        // 洗牌
        Collections.shuffle(mahjongs);
        game.setMahjongs(mahjongs);
    }

    public int mopai(MahjongGame game, int seatIndex) {
        // 没牌了
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
        // 强制清0
        game.setCurrentIndex(0);

        // 每人13张 一共 13*4 ＝ 52张 庄家多一张 53张
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

    // 检查是否可以碰
    public void checkCanPeng(MahjongGame game, GameSeat seatData, int targetPai) {
        if (Card.getMJType(targetPai) == seatData.getQue()) {
            return;
        }
        int count = seatData.getCardCount(targetPai);
        if (count >= 2) {
            seatData.setCanPeng(true);
        }
    }

    // 检查是否可以点杠
    public void checkCanDianGang(MahjongGame game, GameSeat seatData, int targetPai) {
        // 检查玩家手上的牌
        // 如果没有牌了，则不能再杠
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
        // 如果没有牌了，则不能再杠
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

    // 检查是否可以弯杠(自己摸起来的时候)
    public void checkCanWanGang(MahjongGame game, GameSeat seatData) {
        // 如果没有牌了，则不能再杠
        if (game.getMahjongs().size() <= game.getCurrentIndex()) {
            return;
        }
        List<Card> pengs = seatData.getPengs();
        Set<Integer> checkeds = Sets.newHashSet();
        // 从碰过的牌中选
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

    // 检查听牌
    public void checkCanTingPai(MahjongGame game, GameSeat seatData) {
        seatData.setTingMap(Maps.newHashMap());
        Map<Integer, TingCard> tingMap = seatData.getTingMap();
        // 检查手上的牌是不是已打缺，如果未打缺，则不进行判定
        List<Card> holds = seatData.getHolds();
        for (Card card : holds) {
            if (card.getMahjongType() == seatData.getQue()) {
                return;
            }
        }

        // 检查是否是七对 前提是没有碰，也没有杠 ，即手上拥有13张牌
        if (holds.size() == 13) {
            // 有5对牌
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
                    // 如果已经有单牌了，表示不止一张单牌，并没有下叫。直接闪
                    if (danPai >= 0) {
                        break;
                    }
                    danPai = card.getId();
                }
            }
            // 检查是否有6对 并且单牌是不是目标牌
            if (pairCount == 6) {
                // 七对只能和一张，就是手上那张单牌
                // 七对的番数＝ 2番+N个4个牌（即龙七对）
                TingCard tingCard = new TingCard(new Card(danPai), 2, "7pairs");
                tingMap.put(danPai, tingCard);
                // 如果是，则直接返回咯
                return;
            }
        }

        // 检查是否是对对胡 由于四川麻将没有吃，所以只需要检查手上的牌
        // 对对胡叫牌有两种情况
        // 1、N坎 + 1张单牌
        // 2、N-1坎 + 两对牌
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
                // 手上有4个一样的牌，在四川麻将中是和不了对对胡的 随便加点东西
                singleCount++;
                pairCount += 2;
            }
        }

        if ((pairCount == 2 && singleCount == 0) || (pairCount == 0 && singleCount == 1)) {
            for (int i = 0; i < arr.size(); ++i) {
                // 对对胡1番
                int p = arr.get(i);
                if (!tingMap.containsKey(p)) {
                    TingCard tingCard = new TingCard(new Card(p), 1, "duidui");
                    tingMap.put(p, tingCard);
                }
            }
        }
        // 检查是不是平胡
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

    public MahjongGame getGameByUserID(long userId) {

        String roomId = roomContext.getUserRoomId(userId);
        if (StringUtils.isBlank(roomId)) {
            return null;
        }
        Room room = roomContext.getRoom(roomId);
        if (room == null) {
            return null;
        }
        MahjongGame game = room.getMahjongGame();
        return game;
    }

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
            // 如果可以有操作，则进行操作
            this.userContext.sendMessage(seatData.getUserId(), "game_action_push", data);
        } else {
            this.userContext.sendMessage(seatData.getUserId(), "game_action_push");
        }
    }

    public void moveToNextUser(MahjongGame game, int nextSeat) {
        game.setFangpaoshumu(0);
        // 找到下一个没有和牌的玩家
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

    public void doUserMoPai(MahjongGame game) {
        game.setChuPai(-1);
        GameSeat turnSeat = game.getGameSeat(game.getTurn());
        turnSeat.setLastFangGangSeat(-1);
        turnSeat.setGuoHuFan(-1);
        long userId = turnSeat.getUserId();
        int pai = this.mopai(game, game.getTurn());
        // 牌摸完了，结束
        if (pai == -1) {
            doGameOver(game, userId, false);
            return;
        } else {
            int numOfMJ = game.getMahjongs().size() - game.getCurrentIndex();
            this.userContext.broacastInRoom(userId, true, "mj_count_push", numOfMJ);
        }

        recordGameAction(game, game.getTurn(), ACTION_MOPAI, pai);

        // 通知前端新摸的牌
        this.userContext.sendMessage(userId, "game_mopai_push", pai);
        // 检查是否可以暗杠或者胡
        // 检查胡，直杠，弯杠
        if (!turnSeat.isHued()) {
            checkCanAnGang(game, turnSeat);
        }

        // 如果未胡牌，或者摸起来的牌可以杠，才检查弯杠
        if (!turnSeat.isHued() || turnSeat.getHolds().get(turnSeat.getHolds().size() - 1).getId() == pai) {
            checkCanWanGang(game, turnSeat);
        }

        // 检查看是否可以和
        checkCanHu(game, turnSeat, pai);

        // 广播通知玩家出牌方
        turnSeat.setCanChuPai(true);
        this.userContext.broacastInRoom(userId, true, "game_chupai_push", userId);

        // 通知玩家做对应操作
        sendOperations(game, turnSeat, game.getChuPai());
    }

    public boolean isSameType(MahjongType type, List<Card> arr) {
        for (int i = 0; i < arr.size(); ++i) {
            Card card = arr.get(i);
            MahjongType t = card.getMahjongType();
            if (type != null && type != t) {
                return false;
            }
            type = t;
        }
        return true;
    }

    public boolean isQingYiSe(GameSeat gameSeatData) {
        List<Card> holds = gameSeatData.getHolds();
        MahjongType type = Card.getMJType(holds.get(0).getId());
        // 检查手上的牌
        if (!isSameType(type, holds)) {
            return false;
        }

        // 检查杠下的牌
        if (!isSameType(type, gameSeatData.getGangPai()) == false) {
            return false;
        }
        if (isSameType(type, gameSeatData.getWangangs()) == false) {
            return false;
        }
        if (isSameType(type, gameSeatData.getDiangangs()) == false) {
            return false;
        }

        // 检查碰牌
        if (isSameType(type, gameSeatData.getPengs()) == false) {
            return false;
        }
        return true;
    }

    public boolean isMenQing(GameSeat gameSeatData) {
        return (gameSeatData.getPengs().size() + gameSeatData.getWangangs().size() + gameSeatData.getDiangangs().size()) == 0;
    }

    private boolean isZhongZhangFn(List<Card> arr) {
        for (Card card : arr) {
            int pai = card.getId();
            if (pai == 0 || pai == 8 || pai == 9 || pai == 17 || pai == 18 || pai == 26) {
                return false;
            }
        }
        return true;
    }

    public boolean isZhongZhang(GameSeat gameSeatData) {
        if (!isZhongZhangFn(gameSeatData.getPengs())) {
            return false;
        }
        if (!isZhongZhangFn(gameSeatData.getGangPai())) {
            return false;
        }
        if (!isZhongZhangFn(gameSeatData.getDiangangs())) {
            return false;
        }
        if (!isZhongZhangFn(gameSeatData.getWangangs())) {
            return false;
        }
        if (!isZhongZhangFn(gameSeatData.getHolds())) {
            return false;
        }
        return true;
    }

    private boolean isJiangDuiFn(List<Card> arr) {
        for (Card card : arr) {
            int pai = card.getId();
            if (pai != 1 && pai != 4 && pai != 7 && pai != 9 && pai != 13 && pai != 16 && pai != 18 && pai != 21 && pai != 25) {
                return false;
            }
        }
        return true;
    }

    public boolean isJiangDui(GameSeat gameSeatData) {
        if (!isJiangDuiFn(gameSeatData.getPengs())) {
            return false;
        }
        if (!isJiangDuiFn(gameSeatData.getGangPai())) {
            return false;
        }
        if (!isJiangDuiFn(gameSeatData.getDiangangs())) {
            return false;
        }
        if (!isJiangDuiFn(gameSeatData.getWangangs())) {
            return false;
        }
        if (!isJiangDuiFn(gameSeatData.getHolds())) {
            return false;
        }
        return true;
    }

    public boolean isTinged(GameSeat seatData) {
        Map<Integer, TingCard> tingMap = seatData.getTingMap();
        if (tingMap == null) {
            return false;
        }
        return tingMap.size() >= 0;
    }

    public int computeFanScore(MahjongGame game, int fan) {
        if (fan > game.getMaxFun()) {
            fan = game.getMaxFun();
        }
        return (1 << fan) * game.getBaseScore();
    }

    // 是否需要查大叫(有人没有下叫)
    public void needChaDaJiao(MahjongGame game) {
        // 查叫
        var numOfHued = 0;
        var numOfTinged = 0;
        var numOfUntinged = 0;
        for (var i = 0; i < game.gameSeats.length; ++i) {
            var ts = game.gameSeats[i];
            if (ts.hued) {
                numOfHued++;
                numOfTinged++;
            } else if (isTinged(ts)) {
                numOfTinged++;
            } else {
                numOfUntinged++;
            }
        }

        // 如果没有任何一个人叫牌，则不需要查叫
        if (numOfTinged == 0) {
            return false;
        }

        // 如果都听牌了，也不需要查叫
        if (numOfUntinged == 0) {
            return false;
        }
        return true;
    }

    public void findMaxFanTingPai(GameSeat ts){
     //找出最大番
     var cur = null;
     for(var k in ts.tingMap){
     var tpai = ts.tingMap[k];
     if(cur == null || tpai.fan > cur.fan){
     cur = tpai;
     cur.pai = parseInt(k);
     }
     }
     return cur;
     }

    public void findUnTingedPlayers(MahjongGame game){
     var arr = [];
     for(var i = 0; i < game.gameSeats.length; ++i){
     var ts = game.gameSeats[i];
     //如果没有胡，且没有听牌
     if(!ts.hued && !isTinged(ts)){
     arr.push(i);
     }
     }
     return arr;
     }

    public void getNumOfGen(GameSeat seatData){
     var numOfGangs = seatData.diangangs.length + seatData.wangangs.length + seatData.angangs.length;
     for(var k = 0; k < seatData.pengs.length; ++k){
     var pai = seatData.pengs[k];
     if(seatData.countMap[pai] == 1){
     numOfGangs++;
     }
     }
     for(var k in seatData.countMap){
     if(seatData.countMap[k] == 4){
     numOfGangs++;
     }
     }
     return numOfGangs;
     }

    public void chaJiao(MahjongGame game){
     var arr = findUnTingedPlayers(game);
     if(arr.length == 0){
     return;
     }
     for(var i = 0; i < game.gameSeats.length; ++i){
     var ts = game.gameSeats[i];
     //如果听牌了，则未叫牌的人要给钱
     if(isTinged(ts)){
     var cur = findMaxFanTingPai(ts);
     ts.huInfo.push({
     ishupai:true,
     action:"chadajiao",
     fan:cur.fan,
     pattern:cur.pattern,
     pai:cur.pai,
     numofgen:getNumOfGen(ts),
     });

     for(var j = 0; j < arr.length; ++j){
     game.gameSeats[arr[j]].huInfo.push({
     action:"beichadajiao",
     target:i,
     index:ts.huInfo.length-1,
     });
     }
     }
     }
     }

    public void calculateResult(MahjongGame game, Room roomInfo){

     var isNeedChaDaJia = needChaDaJiao(game);
     if(isNeedChaDaJia){
     chaJiao(game);
     }

     var baseScore = game.conf.baseScore;

     for(var i = 0; i < game.gameSeats.length; ++i){
     var sd = game.gameSeats[i];
     //对所有胡牌的玩家进行统计
     if(isTinged(sd)){
     //收杠钱
     var additonalscore = 0;
     for(var a = 0; a < sd.actions.length; ++a){
     var ac = sd.actions[a];
     if(ac.type == "fanggang"){
     var ts = game.gameSeats[ac.targets[0]];
     //检查放杠的情况，如果目标没有和牌，且没有叫牌，则不算 用于优化前端显示
     if(isNeedChaDaJia && (ts.hued) == false && (isTinged(ts) == false)){
     ac.state = "nop";
     }
     }
     else if(ac.type == "angang" || ac.type == "wangang" || ac.type == "diangang"){
     if(ac.state != "nop"){
     var acscore = ac.score;
     additonalscore += ac.targets.length * acscore * baseScore;
     //扣掉目标方的分
     for(var t = 0; t < ac.targets.length; ++t){
     var six = ac.targets[t];
     game.gameSeats[six].score -= acscore * baseScore;
     }
     }
     }
     else if(ac.type == "maozhuanyu"){
     //对于呼叫转移，如果对方没有叫牌，表示不得行
     if(isTinged(ac.owner)){
     //如果
     var ref = ac.ref;
     var acscore = ref.score;
     var total = ref.targets.length * acscore * baseScore;
     additonalscore += total;
     //扣掉目标方的分
     if(ref.payTimes == 0){
     for(var t = 0; t < ref.targets.length; ++t){
     var six = ref.targets[t];
     game.gameSeats[six].score -= acscore * baseScore;
     }
     }
     else{
     //如果已经被扣过一次了，则由杠牌这家赔
     ac.owner.score -= total;
     }
     ref.payTimes++;
     ac.owner = null;
     ac.ref = null;
     }
     }
     }

     if(isQingYiSe(sd)){
     sd.qingyise = true;
     }

     if(game.conf.menqing){
     sd.isMenQing = isMenQing(sd);
     }

     //金钩胡
     if(sd.holds.length == 1 || sd.holds.length == 2){
     sd.isJinGouHu = true;
     }

     sd.numAnGang = sd.angangs.length;
     sd.numMingGang = sd.wangangs.length + sd.diangangs.length;

     //进行胡牌结算
     for(var j = 0; j < sd.huInfo.length; ++j){
     var info = sd.huInfo[j];
     if(!info.ishupai){
     sd.numDianPao++;
     continue;
     }
     //统计自己的番子和分数
     //基础番(平胡0番，对对胡1番、七对2番) + 清一色2番 + 杠+1番
     //杠上花+1番，杠上炮+1番 抢杠胡+1番，金钩胡+1番，海底胡+1番
     var fan = info.fan;
     sd.holds.push(info.pai);
     if(sd.countMap[info.pai] != null){
     sd.countMap[info.pai] ++;
     }
     else{
     sd.countMap[info.pai] = 1;
     }

     if(sd.qingyise){
     fan += 2;
     }

     //金钩胡
     if(sd.isJinGouHu){
     fan += 1;
     }

     if(info.isHaiDiHu){
     fan += 1;
     }

     if(game.conf.tiandihu){
     if(info.isTianHu){
     fan += 3;
     }
     else if(info.isDiHu){
     fan += 2;
     }
     }

     var isjiangdui = false;
     if(game.conf.jiangdui){
     if(info.pattern == "7pairs"){
     if(info.numofgen > 0){
     info.numofgen -= 1;
     info.pattern == "l7pairs";
     isjiangdui = isJiangDui(sd);
     if(isjiangdui){
     info.pattern == "j7paris";
     fan += 2;
     }
     else{
     fan += 1;
     }
     }
     }
     else if(info.pattern == "duidui"){
     isjiangdui = isJiangDui(sd);
     if(isjiangdui){
     info.pattern = "jiangdui";
     fan += 2;
     }
     }
     }

     if(game.conf.menqing){
     //不是将对，才检查中张
     if(!isjiangdui){
     sd.isZhongZhang = isZhongZhang(sd);
     if(sd.isZhongZhang){
     fan += 1;
     }
     }

     if(sd.isMenQing){
     fan += 1;
     }
     }

     fan += info.numofgen;

     if(info.action == "ganghua" || info.action == "dianganghua" || info.action == "gangpaohu" || info.action == "qiangganghu"){
     fan += 1;
     }

     var extraScore = 0;
     if(info.iszimo){
     if(game.conf.zimo == 0){
     //自摸加底
     extraScore = baseScore;
     }
     else if(game.conf.zimo == 1){
     fan += 1;
     }
     else{
     //nothing.
     }
     }
     //和牌的玩家才加这个分
     var score = computeFanScore(game,fan) + extraScore;
     if(info.action == "chadajiao"){
     //收所有没有叫牌的人的钱
     for(var t = 0; t < game.gameSeats.length; ++t){
     if(!isTinged(game.gameSeats[t])){
     game.gameSeats[t].score -= score;
     sd.score += score;
     //被查叫次数
     if(game.gameSeats[t] != sd){
     game.gameSeats[t].numChaJiao++;
     }
     }
     }
     }
     else if(info.iszimo){
     //收所有人的钱
     sd.score += score * game.gameSeats.length;
     for(var t = 0; t < game.gameSeats.length; ++t){
     game.gameSeats[t].score -= score;
     }
     sd.numZiMo++;
     }
     else{
     //收放炮者的钱
     sd.score += score;
     game.gameSeats[info.target].score -= score;
     sd.numJiePao++;
     }

     //撤除胡的那张牌
     sd.holds.pop();
     sd.countMap[info.pai]--;

     if(fan > game.conf.maxFan){
     fan = game.conf.maxFan;
     }
     info.fan = fan;
     }
     //一定要用 += 。 因为此时的sd.score可能是负的
     sd.score += additonalscore;
     }
     else{
     for(var a = sd.actions.length -1; a >= 0; --a){
     var ac = sd.actions[a];
     if(ac.type == "angang" || ac.type == "wangang" || ac.type == "diangang"){
     //如果3家都胡牌，则需要结算。否则认为是查叫
     if(isNeedChaDaJia){
     sd.actions.splice(a,1);
     }
     else{
     if(ac.state != "nop"){
     var acscore = ac.score;
     sd.score += ac.targets.length * acscore * baseScore;
     //扣掉目标方的分
     for(var t = 0; t < ac.targets.length; ++t){
     var six = ac.targets[t];
     game.gameSeats[six].score -= acscore * baseScore;
     }
     }
     }
     }
     }
     }
     }
     }

    private void fnNoticeResult(Room room, long userId, JSONArray results, boolean isEnd) {
        JSONArray endinfo = new JSONArray();
        if (isEnd) {
            for (Seat seat : room.allSeat()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("numzimo", seat.getNumZiMo());
                jsonObject.put("numjiepao", seat.getNumJiePao());
                jsonObject.put("numdianpao", seat.getNumDianPao());
                jsonObject.put("numangang", seat.getNumAnGang());
                jsonObject.put("numminggang", seat.getNumMingGang());
                jsonObject.put("numchadajiao", seat.getNumChaJiao());
                endinfo.add(jsonObject);
            }
        }

        JSONObject resultData = new JSONObject();
        resultData.put("results", results);
        resultData.put("endinfo", endinfo);
        this.userContext.broacastInRoom(userId, true, "game_over_push", resultData);
        // 如果局数已够，则进行整体结算，并关闭房间
        if (isEnd) {
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                if (room.getNumOfGames() > 1) {
                    store_history(room);
                }
                String roomId = room.getId();
                userContext.kickAllInRoom(roomId);
                roomContext.destroy(roomId);
                // db.archive_games(roomInfo.uuid);
            }, 1500, TimeUnit.MILLISECONDS);
        }
    }

    public void doGameOver(MahjongGame game, long userId, boolean forceEnd) {
        String roomId = roomContext.getUserRoomId(userId);
        if (StringUtils.isBlank(roomId)) {
            return;
        }
        Room roomInfo = roomContext.getRoom(roomId);
        if (roomInfo == null) {
            return;
        }

        JSONArray results = new JSONArray();

        int[] dbresult = new int[] { 0, 0, 0, 0 };

        if (game != null) {
            if (!forceEnd) {
                calculateResult(game, roomInfo);
            }

            for (int i = 0; i < roomInfo.allSeat().size(); ++i) {
                Seat rs = roomInfo.allSeat().get(i);
                GameSeat sd = game.getGameSeat(i);
                rs.setReady(false);
                rs.setScore(rs.getScore() + sd.getScore());
                rs.setNumZiMo(rs.getNumZiMo() + sd.getNumZiMo());
                rs.setNumJiePao(rs.getNumJiePao() + sd.getNumJiePao());
                rs.setNumDianPao(rs.getNumDianPao() + sd.getNumDianPao());
                rs.setNumAnGang(rs.getNumAnGang() + sd.getNumAnGang());
                rs.setNumMingGang(rs.getNumMingGang() + sd.getNumMingGang());
                rs.setNumChaJiao(rs.getNumChaJiao() + sd.getNumChaJiao());

                JSONObject userRT = new JSONObject();
                userRT.put("userId", sd.getUserId());

                userRT.put("pengs", sd.getPengs());
                userRT.put("wangangs", sd.getWangangs());
                userRT.put("diangangs", sd.getDiangangs());
                userRT.put("angangs", sd.getGangPai());
                userRT.put("holds", sd.getHolds());
                userRT.put("score", sd.getScore());
                userRT.put("totalscore", rs.getScore());
                userRT.put("qingyise", sd.isQingyise());
                userRT.put("menqing", sd.isMenQing());
                userRT.put("jingouhu", sd.isJinGouHu());
                userRT.put("huinfo", sd.isHuInfo());

                JSONArray actions = new JSONArray();
                for (int actionType : sd.getActions()) {
                    JSONObject type = new JSONObject();
                    type.put("type", actionType);
                }
                userRT.put("actions", actions);
                results.add(userRT);

                dbresult[i] = sd.getScore();
                // delete gameSeatsOfUsers[ sd.userId];
            }
            // delete games[ roomId];

            int old = roomInfo.getNextButton();
            if (game.getYipaoduoxiang() >= 0) {
                roomInfo.setNextButton(game.getYipaoduoxiang());
            } else if (game.getFirstHupai() >= 0) {
                roomInfo.setNextButton(game.getFirstHupai());
            } else {
                roomInfo.setNextButton(((game.getTurn() + 1) % 4));
            }

            if (old != roomInfo.getNextButton()) {
                // db.update_next_button(roomId, roomInfo.nextButton);
            }
        }

        if (forceEnd || game == null) {
            fnNoticeResult(roomInfo, userId, results, true);
        } else {
            // 保存游戏
            store_game();
        }
    }

    private void store_game() {
        // db.update_game_result(roomInfo.uuid, game.gameIndex, dbresult);
        //
        // //记录玩家操作
        // var str = JSON.stringify(game.actionList);
        // db.update_game_action_records(roomInfo.uuid, game.gameIndex, str);
        //
        // //保存游戏局数
        // db.update_num_of_turns(roomId, roomInfo.numOfGames);
        //
        // //如果是第一次，则扣除房卡
        // if (roomInfo.numOfGames == 1) {
        // var cost = 2;
        // if (roomInfo.conf.maxGames == 8) {
        // cost = 3;
        // }
        // db.cost_gems(game.gameSeats[0].userId, cost);
        // }
        //
        // var isEnd = (roomInfo.numOfGames >= roomInfo.conf.maxGames);
        // fnNoticeResult(isEnd);
        // });
    }

    public void recordUserAction(MahjongGame game, GameSeat seatData,type,target){
     var d = {type:type,targets:[]};
     if(target != null){
     if(typeof(target) == 'number'){
     d.targets.push(target);
     }
     else{
     d.targets = target;
     }
     }
     else{
     for(var i = 0; i < game.gameSeats.length; ++i){
     var s = game.gameSeats[i];
     //血流成河，所有自摸，暗杠，弯杠，都算三家
     if(i != seatData.seatIndex/* && s.hued == false*/){
     d.targets.push(i);
     }
     }
     }

     seatData.actions.push(d);
     return d;
     }

    public void recordGameAction(MahjongGame game, int si, int action, int pai) {
        game.getActionList().add(si);
        game.getActionList().add(action);
        if (pai > 0) {
            game.getActionList().add(pai);
        }
    }
    //

    public void setReady() {
        // exports.setReady = public void(userId,callback){
        // var roomId = roomMgr.getUserRoom(userId);
        // if(roomId == null){
        // return;
        // }
        // var roomInfo = roomMgr.getRoom(roomId);
        // if(roomInfo == null){
        // return;
        // }
        //
        // roomMgr.setReady(userId,true);
        //
        // var game = games[roomId];
        // if(game == null){
        // if(roomInfo.seats.length == 4){
        // for(var i = 0; i < roomInfo.seats.length; ++i){
        // var s = roomInfo.seats[i];
        // if(s.ready == false || userMgr.isOnline(s.userId)==false){
        // return;
        // }
        // }
        // //4个人到齐了，并且都准备好了，则开始新的一局
        // exports.begin(roomId);
        // }
        // }
        // else{
        // var numOfMJ = game.mahjongs.length - game.currentIndex;
        // var remainingGames = roomInfo.conf.maxGames - roomInfo.numOfGames;
        //
        // var data = {
        // state:game.state,
        // numofmj:numOfMJ,
        // button:game.button,
        // turn:game.turn,
        // chuPai:game.chuPai,
        // };
        //
        // data.seats = [];
        // var seatData = null;
        // for(var i = 0; i < 4; ++i){
        // var sd = game.gameSeats[i];
        //
        // var s = {
        // userid:sd.userId,
        // folds:sd.folds,
        // angangs:sd.angangs,
        // diangangs:sd.diangangs,
        // wangangs:sd.wangangs,
        // pengs:sd.pengs,
        // que:sd.que,
        // hued:sd.hued,
        // huinfo:sd.huInfo,
        // iszimo:sd.iszimo,
        // }
        // if(sd.userId == userId){
        // s.holds = sd.holds;
        // s.huanpais = sd.huanpais;
        // seatData = sd;
        // }
        // else{
        // s.huanpais = sd.huanpais? []:null;
        // }
        // data.seats.push(s);
        // }
        //
        // //同步整个信息给客户端
        // userMgr.sendMsg(userId,'game_sync_push',data);
        // sendOperations(MahjongGame game, GameSeat seatData,game.chuPai);
        // }
        // }
    }

    public void store_single_history(long userId, JSONObject history) {
        // db.get_user_history(userId,public void(data){
        // if(data == null){
        // data = [];
        // }
        // while(data.length >= 10){
        // data.shift();
        // }
        // data.push(history);
        // db.update_user_history(userId,data);
        // });
    }

    public void store_history(Room roomInfo) {
        List<Seat> seats = roomInfo.allSeat();
        JSONObject history = new JSONObject();
        history.put("uuid", roomInfo.getUuid());
        history.put("id", roomInfo.getId());
        history.put("time", roomInfo.getCreateTime());
        history.put("seats", new JSONArray());
        JSONArray seatArray = new JSONArray();
        for (int i = 0; i < seats.size(); ++i) {
            Seat rs = seats.get(i);
            JSONObject hs = new JSONObject();
            hs.put("userid", rs.getUserId());
            hs.put("name", rs.getName());
            hs.put("score", rs.getScore());
            // hs.name = crypto.toBase64(rs.name);
        }

        for (int i = 0; i < seats.size(); ++i) {
            Seat rs = seats.get(i);
            store_single_history(rs.getUserId(), history);
        }

    }
    //
    //
    // public void construct_game_base_info(game){
    // var baseInfo = {
    // type:game.conf.type,
    // button:game.button,
    // index:game.gameIndex,
    // mahjongs:game.mahjongs,
    // game_seats:new Array(4)
    // }
    // for(var i = 0; i < 4; ++i){
    // baseInfo.game_seats[i] = game.gameSeats[i].holds;
    // }
    // game.baseInfoJson = JSON.stringify(baseInfo);
    // }
    //
    // public void store_game(game,callback){
    // db.create_game(game.roomInfo.uuid,game.gameIndex,game.baseInfoJson,callback);
    // }
    //
    //// 开始新的一局
    // exports.begin = public void(roomId) {
    // var roomInfo = roomMgr.getRoom(roomId);
    // if(roomInfo == null){
    // return;
    // }
    // var seats = roomInfo.seats;
    //
    // var game = {
    // conf:roomInfo.conf,
    // roomInfo:roomInfo,
    // gameIndex:roomInfo.numOfGames,
    //
    // button:roomInfo.nextButton,
    // mahjongs:new Array(108),
    // currentIndex:0,
    // gameSeats:new Array(4),
    //
    // numOfQue:0,
    // turn:0,
    // chuPai:-1,
    // state:"idle",
    // firstHupai:-1,
    // yipaoduoxiang:-1,
    // fangpaoshumu:-1,
    // actionList:[],
    // chupaiCnt:0,
    // };
    //
    // roomInfo.numOfGames++;
    //
    // for(var i = 0; i < 4; ++i){
    // var data = game.gameSeats[i] = {};
    //
    // data.game = game;
    //
    // data.seatIndex = i;
    //
    // data.userId = seats[i].userId;
    // //持有的牌
    // data.holds = [];
    // //打出的牌
    // data.folds = [];
    // //暗杠的牌
    // data.angangs = [];
    // //点杠的牌
    // data.diangangs = [];
    // //弯杠的牌
    // data.wangangs = [];
    // //碰了的牌
    // data.pengs = [];
    // //缺一门
    // data.que = -1;
    //
    // //换三张的牌
    // data.huanpais = null;
    //
    // //玩家手上的牌的数目，用于快速判定碰杠
    // data.countMap = {};
    // //玩家听牌，用于快速判定胡了的番数
    // data.tingMap = {};
    // data.pattern = "";
    //
    // //是否可以杠
    // data.canGang = false;
    // //用于记录玩家可以杠的牌
    // data.gangPai = [];
    //
    // //是否可以碰
    // data.canPeng = false;
    // //是否可以胡
    // data.canHu = false;
    // //是否可以出牌
    // data.canChuPai = false;
    //
    // //如果guoHuFan >=0 表示处于过胡状态，
    // //如果过胡状态，那么只能胡大于过胡番数的牌
    // data.guoHuFan = -1;
    //
    // //是否胡了
    // data.hued = false;
    // //
    // data.actions = [];
    //
    // //是否是自摸
    // data.iszimo = false;
    // data.isGangHu = false;
    // data.fan = 0;
    // data.score = 0;
    // data.huInfo = [];
    //
    //
    // data.lastFangGangSeat = -1;
    //
    // //统计信息
    // data.numZiMo = 0;
    // data.numJiePao = 0;
    // data.numDianPao = 0;
    // data.numAnGang = 0;
    // data.numMingGang = 0;
    // data.numChaJiao = 0;
    //
    // gameSeatsOfUsers[data.userId] = data;
    // }
    // games[roomId] = game;
    // //洗牌
    // shuffle(game);
    // //发牌
    // deal(game);
    //
    //
    //
    // var numOfMJ = game.mahjongs.length - game.currentIndex;
    // var huansanzhang = roomInfo.conf.hsz;
    //
    // for(var i = 0; i < seats.length; ++i){
    // //开局时，通知前端必要的数据
    // var s = seats[i];
    // //通知玩家手牌
    // userMgr.sendMsg(s.userId,'game_holds_push',game.gameSeats[i].holds);
    // //通知还剩多少张牌
    // userMgr.sendMsg(s.userId,'mj_count_push',numOfMJ);
    // //通知还剩多少局
    // userMgr.sendMsg(s.userId,'game_num_push',roomInfo.numOfGames);
    // //通知游戏开始
    // userMgr.sendMsg(s.userId,'game_begin_push',game.button);
    //
    // if(huansanzhang == true){
    // game.state = "huanpai";
    // //通知准备换牌
    // userMgr.sendMsg(s.userId,'game_huanpai_push');
    // }
    // else{
    // game.state = "dingque";
    // //通知准备定缺
    // userMgr.sendMsg(s.userId,'game_dingque_push');
    // }
    // }
    // }
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
