package com.liubowen.socketiomahjong.domain.user;

import com.liubowen.socketiomahjong.entity.UserInfo;

/**
 * @author liubowen
 * @date 2017/11/10 10:24
 * @description
 */
public class User {

    private UserInfo userInfo;

    private String sessionId;

    public User(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void bind(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long userId() {
        return this.userInfo.getUserId();
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public boolean hasSession() {
        return this.sessionId != null && !"".equals(this.sessionId);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
