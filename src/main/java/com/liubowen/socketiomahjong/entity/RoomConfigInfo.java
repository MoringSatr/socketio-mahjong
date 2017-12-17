package com.liubowen.socketiomahjong.entity;

import com.liubowen.socketiomahjong.domain.game.GameType;
import lombok.Data;

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
@lombok.NoArgsConstructor
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
    private int jiangdui;

    @Column(name = "`huansanzhang`")
    private int huansanzhang;

    @Column(name = "`zuidafanshu`")
    private int zuidafanshu;

    @Column(name = "`jushuxuanze`")
    private int jushuxuanze;

    @Column(name = "`dianganghua`")
    private int dianganghua;

    @Column(name = "`menqing`")
    private int menqing;

    @Column(name = "`tiandihu`")
    private int tiandihu;

}
