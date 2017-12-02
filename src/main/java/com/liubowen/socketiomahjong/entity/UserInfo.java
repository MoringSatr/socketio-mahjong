package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "t_users")
public class UserInfo {

    /** 用户ID */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userId;

    /** 账号 */
    @Column(name = "account")
    private String account;

    /** 用户昵称 */
    @Column(name = "name")
    private String name;

    /** 用户性别 */
    @Column(name = "sex")
    private Integer sex;

    /** 用户头像 */
    @Column(name = "headimg")
    private String headImg;

    /** 用户等级 */
    @Column(name = "lv")
    private Integer level;

    /** 用户经验 */
    @Column(name = "exp")
    private Integer exp;

    /** 用户金币 */
    @Column(name = "coins")
    private Integer coins;

    /** 用户宝石 */
    @Column(name = "gems")
    private Integer gems;

    /** 房间id */
    @Column(name = "roomid")
    private String roomId;

    /** 用户历史记录 */
    @Column(name = "history")
    private String history;

    public UserInfo() {
    }

    public UserInfo(String account, String name, Integer sex, String headImg, Integer coins, Integer gems) {
        this.account = account;
        this.name = name;
        this.sex = sex;
        this.headImg = headImg;
        this.coins = coins;
        this.gems = gems;
    }
}
