package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.domain.room.Room;
import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.RoomInfoMapper;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.service.ClientService;
import com.liubowen.socketiomahjong.service.RoomDomainService;
import com.liubowen.socketiomahjong.service.UserDomainService;
import com.liubowen.socketiomahjong.util.encode.Md5Util;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import com.liubowen.socketiomahjong.util.time.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liubowen
 * @date 2017/11/10 20:16
 * @description
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private RoomDomainService roomDomainService;

    private boolean checkAccount(String account, String sign, String ip) {
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(sign)) {
            return false;
        }
        String serverSign = Md5Util.MD5(account + ip + Constant.ACCOUNT_PRI_KEY);
        if (!sign.equals(serverSign)) {
            return false;
        }
        return true;
    }

    @Override
    public ResultEntity login(String account, String sign, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err(2, "login failed.");
        }
        UserInfo userInfo = this.userInfoMapper.findUserInfoByAccount(account);
        if (userInfo == null) {
            return ResultEntityUtil.ok();
        }
        ResultEntity resultEntity = ResultEntityUtil.ok();
        resultEntity.add("account", userInfo.getAccount());
        resultEntity.add("userid", userInfo.getUserId());
        resultEntity.add("name", userInfo.getName());
        resultEntity.add("lv", userInfo.getLevel());
        resultEntity.add("exp", userInfo.getExp());
        resultEntity.add("coins", userInfo.getCoins());
        resultEntity.add("gems", userInfo.getGems());
        resultEntity.add("ip", ip);
        resultEntity.add("sex", userInfo.getSex());
        String roomId = userInfo.getRoomId();
        if (!StringUtils.isEmpty(roomId)) {
            boolean isRoomExist = this.roomInfoMapper.isRoomExist(roomId);
            if (isRoomExist) {
                resultEntity.add("roomid", roomId);
            } else {
                userInfo.setRoomId("");
                this.userInfoMapper.updateByPrimaryKey(userInfo);
            }
        }
        return resultEntity;
    }

    @Override
    public ResultEntity createUser(String account, String sign, String name, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err(2, "login failed.");
        }
        boolean isUserExist = this.userInfoMapper.isUserExistByAccount(account);
        if (!isUserExist) {
            this.userDomainService.createUser(account, name, Constant.randomSex(), Constant.randomHeadImage(), 1000, 21);
            return ResultEntityUtil.ok();
        } else {
            return ResultEntityUtil.err("account have already exist.");
        }
    }

    @Override
    public ResultEntity createPrivateRoom(String account, String sign, long userId, String name, String conf, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err(2, "login failed.");
        }
        UserInfo userInfo = this.userInfoMapper.findUserInfoByAccount(account);
        if (userInfo == null) {
            return ResultEntityUtil.err("system error");
        }
        boolean isRoomExist = this.roomInfoMapper.isRoomExist(userInfo.getRoomId());
        if (isRoomExist) {
            return ResultEntityUtil.err(-1, "user is playing in room now.");
        }

        ResultEntity createRoomResult = this.roomDomainService.createRoom(account, userId, conf);
        String roomId = createRoomResult.get("roomId").toString();
        if (createRoomResult.ok() && StringUtils.isBlank(roomId)) {
            ResultEntity enterRoomResult = this.roomDomainService.enterRoom(userId, name, roomId);
            Object enterInfo = enterRoomResult.get("enterInfo");
            if (enterInfo != null) {
                //TODO
                ResultEntity result = ResultEntityUtil.ok();
                result.add("roomid", roomId);
                result.add("ip", ip);
                result.add("port", 0);
                result.add("token", "");
                result.add("time", TimeUtil.currentTimeMillis());
                return result;
            } else {
                return ResultEntityUtil.err("room doesn't exist.");
            }
        }
        return ResultEntityUtil.err("create failed.");
    }

    @Override
    public ResultEntity enterPrivateRoom(String roomId, String account, long userId, String name, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResultEntity getHistoryList(String account, String sign, long userId, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err(2, "login failed.");
        }
        return null;
    }

    @Override
    public ResultEntity getGamesOfRoom(String uuid, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResultEntity getDetailOfGame(String account, String sign, String uuid, int index, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err(2, "login failed.");
        }
        return null;
    }

    @Override
    public ResultEntity getUserStatus(String account, String sign, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err(2, "login failed.");
        }
        UserInfo userInfo = this.userInfoMapper.findUserInfoByAccount(account);
        if(userInfo == null) {
            return ResultEntityUtil.err("user not find.");
        }
        ResultEntity resultEntity = ResultEntityUtil.ok();
        resultEntity.add("gems", userInfo.getGems());
        return resultEntity;
    }

    @Override
    public ResultEntity getMessage(String account, String sign, String type, String version, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err(2, "login failed.");
        }
        ResultEntity resultEntity = ResultEntityUtil.ok();
        resultEntity.add("msg", "我是刘博文");
        resultEntity.add("version", Constant.VERSION);
        return resultEntity;
    }

    @Override
    public ResultEntity isServerOnline(String account, String sign, String ip, int port, HttpServletRequest request) {
        return null;
    }

}
