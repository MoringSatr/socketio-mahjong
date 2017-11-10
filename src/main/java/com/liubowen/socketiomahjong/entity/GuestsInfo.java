package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author liubowen
 * @date 2017/11/10 3:27
 * @description
 */
@Data
@Table(name = "t_guests")
public class GuestsInfo {

    @Column(name = "guest_account")
    private String guestAccount;

}
