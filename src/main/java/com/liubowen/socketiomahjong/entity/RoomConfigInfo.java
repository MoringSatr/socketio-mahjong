package com.liubowen.socketiomahjong.entity;

import com.liubowen.socketiomahjong.domain.game.GameType;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author liubowen
 * @date 2017/12/14 12:57
 * @description
 */
@Data
@Table(name = "room_config")
public class RoomConfigInfo {

    private GameType gameType;

    private int button;

    private int index;

}
