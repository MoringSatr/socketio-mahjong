package com.liubowen.socketiomahjong.entity;

import com.google.common.collect.Lists;
import com.liubowen.socketiomahjong.domain.room.RoomConfig;
import com.liubowen.socketiomahjong.domain.room.RoomPlayer;
import com.liubowen.socketiomahjong.util.time.TimeUtil;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/10 3:20
 * @description
 */
@Data
@Table(name = "t_rooms")
public class RoomInfo {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "id")
    private String id;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "num_of_turns")
    private Integer numOfTurns;

    @Column(name = "next_button")
    private int nextButton;

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private int port;

    private List<RoomPlayer> roomPlayers;

    private RoomConfig roomConfig;

    public RoomInfo(String id, String ip, int port, RoomConfig roomConfig) {
        this.id = id;
        this.createTime = TimeUtil.currentTimeMillis();
        this.numOfTurns = 0;
        this.nextButton = 0;
        this.ip = ip;
        this.port = port;
        this.roomPlayers = Lists.newArrayList();
        this.roomConfig = roomConfig;
    }

}
