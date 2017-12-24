package com.liubowen.socketiomahjong.domain.game;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liubowen
 * @date 2017/12/24 23:51
 * @description
 */
@Data
@AllArgsConstructor
public class TingCard {

    private Card card;

    private int fun;

    private String pattern;

}
