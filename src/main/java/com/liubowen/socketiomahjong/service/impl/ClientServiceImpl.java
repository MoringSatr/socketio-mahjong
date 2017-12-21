package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.domain.room.RoomContext;
import com.liubowen.socketiomahjong.dto.EnterInfoDto;
import com.liubowen.socketiomahjong.entity.MessageInfo;
import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.MessageInfoMapper;
import com.liubowen.socketiomahjong.mapper.RoomInfoMapper;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.service.ClientService;
import com.liubowen.socketiomahjong.service.RoomDomainService;
import com.liubowen.socketiomahjong.service.UserDomainService;
import com.liubowen.socketiomahjong.util.encode.Md5Util;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import com.liubowen.socketiomahjong.util.time.TimeUtil;
import com.liubowen.socketiomahjong.vo.GameConfVo;
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

    @Autowired
    private MessageInfoMapper messageInfoMapper;

    @Autowired
    private RoomContext roomContext;

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
            return ResultEntityUtil.err(0, "user not exist.");
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
            boolean isRoomExist = this.roomContext.isRoomExist(roomId);
//            boolean isRoomExist = this.roomInfoMapper.isRoomExist(roomId);
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
    public ResultEntity createPrivateRoom(String account, String sign, GameConfVo conf, HttpServletRequest request) {
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
            return ResultEntityUtil.err("user is playing in room now.");
        }
        long userId = userInfo.getUserId();
        String name = userInfo.getName();

        ResultEntity createRoomResult = this.roomDomainService.createRoom(account, userId, conf);
        if (!createRoomResult.containsKey("roomId")) {
            return ResultEntityUtil.err("create failed.");
        }
        String roomId = createRoomResult.get("roomId").toString();
        if (createRoomResult.ok() && !StringUtils.isBlank(roomId)) {
            ResultEntity enterRoomResult = this.roomDomainService.enterRoom(userId, name, roomId);
            EnterInfoDto enterInfo = (EnterInfoDto) enterRoomResult.get("enterInfo");
            if (enterInfo != null) {
                // TODO
                ResultEntity result = ResultEntityUtil.ok();
                result.add("roomid", enterInfo.getRoomId());
                result.add("ip", enterInfo.getIp());
                result.add("port", enterInfo.getPort());
                result.add("token", enterInfo.getToken());
                long time = TimeUtil.currentTimeMillis();
                result.add("time", time);
                result.add("sign", Md5Util.MD5(enterInfo.getRoomId() + enterInfo.getToken() + time + Constant.ROOM_PRI_KEY));
                return result;
            } else {
                return ResultEntityUtil.err("room doesn't exist.");
            }
        }
        return ResultEntityUtil.err("create failed.");
    }

    @Override
    public ResultEntity enterPrivateRoom(String account, String sign, String roomId, HttpServletRequest request) {
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
            return ResultEntityUtil.err("user is playing in room now.");
        }
        if (!StringUtils.isBlank(roomId)) {
            ResultEntity enterRoomResult = this.roomDomainService.enterRoom(userInfo.getUserId(), userInfo.getName(), roomId);
            EnterInfoDto enterInfo = (EnterInfoDto) enterRoomResult.get("enterInfo");
            if (enterInfo != null) {
                // TODO
                ResultEntity result = ResultEntityUtil.ok();
                result.add("roomid", enterInfo.getRoomId());
                result.add("ip", enterInfo.getIp());
                result.add("port", enterInfo.getPort());
                result.add("token", enterInfo.getToken());
                long time = TimeUtil.currentTimeMillis();
                result.add("time", time);
                result.add("sign", Md5Util.MD5(enterInfo.getRoomId() + enterInfo.getToken() + time + Constant.ROOM_PRI_KEY));
                return result;
            } else {
                return ResultEntityUtil.err("room doesn't exist.");
            }
        }
        return ResultEntityUtil.err("roomId is blank.");
    }

    @Override
    public ResultEntity getHistoryList(String account, String sign, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err("login failed.");
        }
        return ResultEntityUtil.ok();
    }

    @Override
    public ResultEntity getGamesOfRoom(String account, String sign, String uuid, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResultEntity getDetailOfGame(String account, String sign, String uuid, int index, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err("login failed.");
        }
        return null;
    }

    @Override
    public ResultEntity getUserStatus(String account, String sign, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err("login failed.");
        }
        UserInfo userInfo = this.userInfoMapper.findUserInfoByAccount(account);
        if (userInfo == null) {
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
            return ResultEntityUtil.err("login failed.");
        }
        MessageInfo messageInfo = this.messageInfoMapper.findMessageInfoByTypeAndVersion(type, version);
        ResultEntity resultEntity = ResultEntityUtil.ok();
        if (messageInfo == null) {
            return ResultEntityUtil.err("get message failed.");
        }
        resultEntity.add("msg", messageInfo.getMsg());
        resultEntity.add("version", messageInfo.getVersion());
        return resultEntity;
    }

    @Override
    public ResultEntity isServerOnline(String account, String sign, String ip, int port, HttpServletRequest request) {
        // String ip = Constant.getIpByRequest(request);
        if (!checkAccount(account, sign, ip)) {
            return ResultEntityUtil.err("login failed.");
        }
        ResultEntity resultEntity = ResultEntityUtil.ok();
        // TODO
        resultEntity.add("isonline", true);
        return resultEntity;
    }

}
