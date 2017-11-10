package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "base_info")
    private String baseInfo;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "num_of_turns")
    private Integer numOfTurns;

    @Column(name = "next_button")
    private int nextButton;

    @Column(name = "user_id0")
    private Long userId0;

    @Column(name = "user_icon0")
    private String userIcon0;

    @Column(name = "user_name0")
    private String userName0;

    @Column(name = "user_score0")
    private Integer userScore0;

    @Column(name = "user_id1")
    private Long userId1;

    @Column(name = "user_icon1")
    private String userIcon1;

    @Column(name = "user_name1")
    private String userName1;

    @Column(name = "user_score1")
    private Integer userScore1;

    @Column(name = "user_id2")
    private Long userId2;

    @Column(name = "user_icon2")
    private String userIcon2;

    @Column(name = "user_name2")
    private String userName2;

    @Column(name = "user_score2")
    private Integer userScore2;

    @Column(name = "user_id3")
    private Long userId3;

    @Column(name = "user_icon3")
    private String userIcon3;

    @Column(name = "user_name3")
    private String userName3;

    @Column(name = "user_score3")
    private Integer userScore3;

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private String port;

}
