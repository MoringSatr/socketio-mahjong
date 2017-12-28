package com.liubowen.socketiomahjong.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liubowen
 * @date 2017/12/29 1:51
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestSeatPlayer {

    /** 玩家id */
    private long playerId;

    /** 玩家名称 */
    private String nickname;

    /** 玩家头像 */
    private String headImage;

    /** 玩家积分 */
    private int score;

}
