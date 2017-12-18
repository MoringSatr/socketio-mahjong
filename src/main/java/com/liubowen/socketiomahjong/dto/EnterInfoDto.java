package com.liubowen.socketiomahjong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liubowen
 * @date 2017/12/18 23:44
 * @description
 */
@Data
@AllArgsConstructor
public class EnterInfoDto {

    private String roomId;

    private String ip;

    private int port;

    private String token;

}
