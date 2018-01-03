package com.liubowen.socketiomahjong.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liubowen
 * @date 2018/1/1 19:13
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestTingCard {

    private byte card;

    private int fun;

    private String pattern;
}
