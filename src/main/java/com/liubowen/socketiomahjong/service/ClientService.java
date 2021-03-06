package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.vo.GameConfVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liubowen
 * @date 2017/11/10 20:16
 * @description
 */
public interface ClientService {

    ResultEntity login(String account, String sign, HttpServletRequest request);

    ResultEntity createUser(String account, String sign, String name, HttpServletRequest request);

    ResultEntity createPrivateRoom(String account, String sign, GameConfVo conf, HttpServletRequest request);

    ResultEntity enterPrivateRoom(String account, String sign, String roomId, HttpServletRequest request);

    ResultEntity getHistoryList(String account, String sign, HttpServletRequest request);

    ResultEntity getGamesOfRoom(String account, String sign, String uuid, HttpServletRequest request);

    ResultEntity getDetailOfGame(String account, String sign, String uuid, int index, HttpServletRequest request);

    ResultEntity getUserStatus(String account, String sign, HttpServletRequest request);

    ResultEntity getMessage(String account, String sign, String type, String version, HttpServletRequest request);

    ResultEntity isServerOnline(String account, String sign, String ip, int port, HttpServletRequest request);
}
