package com.liubowen.socketiomahjong.entity;

import com.liubowen.socketiomahjong.domain.game.GameType;
import lombok.Data;

import javax.persistence.*;

/**
 * @author liubowen
 * @date 2017/12/14 12:57
 * @description
 */
@Data
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

}
