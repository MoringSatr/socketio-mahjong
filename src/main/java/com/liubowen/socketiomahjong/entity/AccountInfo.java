package com.liubowen.socketiomahjong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liubowen
 * @date 2017/11/29 11:03
 * @description
 */
@Data
@Table(name = "t_accounts")
public class AccountInfo {

    @Id
    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    public AccountInfo(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
