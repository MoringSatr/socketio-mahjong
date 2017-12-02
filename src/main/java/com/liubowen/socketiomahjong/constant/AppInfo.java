package com.liubowen.socketiomahjong.constant;

import lombok.Getter;

/**
 * @author liubowen
 * @date 2017/11/29 13:20
 * @description
 */
@Getter
public enum AppInfo {

    Android("wxe39f08522d35c80c", "fa88e3a3ca5a11b06499902cea4b9c01"), iOS("wxcb508816c5c4e2a4", "7de38489ede63089269e3410d5905038"),;

    private final String appid;

    private final String secret;

    AppInfo(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
    }
}
