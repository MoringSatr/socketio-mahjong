package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author liubowen
 * @date 2017/11/10 3:33
 * @description
 */
@Data
@Table(name = "t_games_archive")
public class GameArchiveInfo {

    @Column(name = "room_uuid")
    private String roomUuid;

    @Column(name = "game_index")
    private Integer gameIndex;

    @Column(name = "base_info")
    private String baseInfo;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "snapshots")
    private String snapshots;

    @Column(name = "action_records")
    private String actionRecords;

    @Column(name = "result")
    private String result;
}
