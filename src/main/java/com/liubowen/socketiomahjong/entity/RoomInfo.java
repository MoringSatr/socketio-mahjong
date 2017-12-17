package com.liubowen.socketiomahjong.entity;

import com.google.common.collect.Lists;
import com.liubowen.socketiomahjong.util.time.TimeUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/10 3:20
 * @description
 */
@Data
@lombok.NoArgsConstructor
@Table(name = "t_rooms")
public class RoomInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select replace(uuid(), '-', '') as id from dual")
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

    @Transient
    private List<RoomPlayerInfo> roomPlayerInfos;

    @Transient
    private RoomConfigInfo roomConfigInfo;

    public RoomInfo(String id, String ip, int port, RoomConfigInfo roomConfigInfo) {
        this.id = id;
        this.createTime = TimeUtil.currentTimeMillis();
        this.numOfTurns = 0;
        this.nextButton = 0;
        this.ip = ip;
        this.port = port;
        this.roomPlayerInfos = Lists.newArrayList();
        this.roomConfigInfo = roomConfigInfo;
    }

}
