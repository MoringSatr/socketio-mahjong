package com.liubowen.socketiomahjong.domain.user;

import com.liubowen.socketiomahjong.util.encode.Md5Util;
import com.liubowen.socketiomahjong.util.time.TimeUtil;
import lombok.Data;

@Data
public class UserToken {

    private Long userId;

    private long time;

    private long lifeTime;

    private String token;

    public UserToken(long userId, long lifeTime) {
        this.userId = userId;
        this.lifeTime = lifeTime;
        this.time = TimeUtil.currentTimeMillis();
        this.encodeToken();
    }

    private void encodeToken() {
        String t = userId + "!@#$%^&" + time;
        this.token = Md5Util.MD5(t);
    }

    public boolean isTokenValid() {
//        if(this.time + this.lifeTime < TimeUtil.currentTimeMillis()){
//            return false;
//        }
        return true;
    }
}
