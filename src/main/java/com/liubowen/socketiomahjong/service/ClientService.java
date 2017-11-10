package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.common.ResultEntity;

/**
 * @author liubowen
 * @date 2017/11/10 20:16
 * @description
 */
public interface ClientService {

    ResultEntity login(String account, String sign);

    ResultEntity createUser(String account, String sign, String name);

    ResultEntity createPrivateRoom(String account, String sign, String userid, String name);

    ResultEntity enterPrivateRoom(String roomid, String account, String userid, String name);

    ResultEntity getHistoryList(String account, String sign, String userid);

    ResultEntity getGamesOfRoom(String uuid);

    ResultEntity getDetailOfGame(String account, String sign, String uuid, String index);

    ResultEntity getUserStatus(String account, String sign);

    ResultEntity getMessage(String account, String sign, String type, String version);

    ResultEntity isServerOnline(String account, String sign, String ip, String port);
}
