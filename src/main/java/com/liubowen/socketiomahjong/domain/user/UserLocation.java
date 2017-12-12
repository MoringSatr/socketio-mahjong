package com.liubowen.socketiomahjong.domain.user;

import com.liubowen.socketiomahjong.common.Saveable;
import lombok.Data;

/**
 * @author liubowen
 * @date 2017/11/10 10:54
 * @description
 */
@Data
public class UserLocation implements Saveable {

    private Long userId;

    private String roomId;

    private int seatIndex;

    @Override
    public void save() {

    }
}
