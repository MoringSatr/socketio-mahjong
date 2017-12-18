package com.liubowen.socketiomahjong.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.domain.game.GameType;
import com.liubowen.socketiomahjong.vo.GameConfVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/14 12:57
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room_config")
public class RoomConfigInfo {

    @Id
    @Column(name = "room_id")
    private String roomId;

    @Column(name = "game_type")
    private GameType gameType;

    @Column(name = "button")
    private int button;

    @Column(name = "`index`")
    private int index;

    @Column(name = "`mahjongs`")
    private List<Integer> mahjongs;

    @Column(name = "`game_seats`")
    private Map<Integer, List<Integer>> gameSeats;

    @Column(name = "`creator_id`")
    private long creatorId;

    @Column(name = "`difen`")
    private int difen;

    @Column(name = "`zimo`")
    private int zimo;

    @Column(name = "`jiangdui`")
    private boolean jiangdui;

    @Column(name = "`huansanzhang`")
    private boolean huansanzhang;

    @Column(name = "`zuidafanshu`")
    private int zuidafanshu;

    @Column(name = "`jushuxuanze`")
    private int jushuxuanze;

    @Column(name = "`dianganghua`")
    private int dianganghua;

    @Column(name = "`menqing`")
    private boolean menqing;

    @Column(name = "`tiandihu`")
    private boolean tiandihu;


    public RoomConfigInfo build(GameConfVo gameConfVo) {
        this.mahjongs = Lists.newArrayList();
        this.gameSeats = Maps.newHashMap();
        if (gameConfVo == null) {
            return this;
        }
        this.gameType = gameConfVo.getGameType();
        this.difen = gameConfVo.getDifen();
        this.zimo = gameConfVo.getZimo();
        this.jiangdui = gameConfVo.isJiangdui();
        this.huansanzhang = gameConfVo.isHuansanzhang();
        this.zuidafanshu = gameConfVo.getZuidafanshu();
        this.jushuxuanze = gameConfVo.getJushuxuanze();
        this.dianganghua = gameConfVo.getDianganghua();
        this.menqing = gameConfVo.isMenqing();
        this.tiandihu = gameConfVo.isTiandihu();
        return this;
    }

}
