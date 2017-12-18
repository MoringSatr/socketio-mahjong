package com.liubowen.socketiomahjong.vo;

import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.util.encode.Md5Util;
import lombok.Data;

/**
 * @author liubowen
 * @date 2017/11/10 4:00
 * @description
 */
@Data
public class LoginVo {

    private String token;

    private String roomId;

    private Long time;

    private String sign;

    public boolean isLegal() {
        return token != null && roomId != null && time != 0 && sign!= null;
    }

    public boolean isMd5() {
        String md5 = Md5Util.MD5(this.roomId + this.token + this.time + Constant.ROOM_PRI_KEY);
        return md5.equals(this.sign);
    }
}
