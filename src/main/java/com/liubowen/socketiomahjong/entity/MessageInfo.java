package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author liubowen
 * @date 2017/11/10 3:25
 * @description
 */
@Data
@Table(name = "t_message")
public class MessageInfo {

    @Column(name = "type")
    private String type;

    @Column(name = "msg")
    private String msg;

    @Column(name = "version")
    private String version;
}
