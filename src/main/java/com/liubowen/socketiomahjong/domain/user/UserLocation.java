package com.liubowen.socketiomahjong.domain.user;

import lombok.Data;

/**
 * @author liubowen
 * @date 2017/11/10 10:54
 * @description
 */
@Data
public class UserLocation {

    private Long userId;

    private String roomId;

    private int seatIndex;
}
