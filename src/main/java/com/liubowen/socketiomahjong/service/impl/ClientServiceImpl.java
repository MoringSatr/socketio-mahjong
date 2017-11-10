package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.service.ClientService;
import org.springframework.stereotype.Service;

/**
 * @author liubowen
 * @date 2017/11/10 20:16
 * @description
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    @Override
    public ResultEntity login(String account, String sign) {
        return null;
    }

    @Override
    public ResultEntity createUser(String account, String sign, String name) {
        return null;
    }

    @Override
    public ResultEntity createPrivateRoom(String account, String sign, String userid, String name) {
        return null;
    }

    @Override
    public ResultEntity enterPrivateRoom(String roomid, String account, String userid, String name) {
        return null;
    }

    @Override
    public ResultEntity getHistoryList(String account, String sign, String userid) {
        return null;
    }

    @Override
    public ResultEntity getGamesOfRoom(String uuid) {
        return null;
    }

    @Override
    public ResultEntity getDetailOfGame(String account, String sign, String uuid, String index) {
        return null;
    }

    @Override
    public ResultEntity getUserStatus(String account, String sign) {
        return null;
    }

    @Override
    public ResultEntity getMessage(String account, String sign, String type, String version) {
        return null;
    }

    @Override
    public ResultEntity isServerOnline(String account, String sign, String ip, String port) {
        return null;
    }
}
