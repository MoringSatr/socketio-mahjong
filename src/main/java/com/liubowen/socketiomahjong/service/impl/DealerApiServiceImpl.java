package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.service.DealerApiService;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubowen
 * @date 2017/11/10 19:39
 * @description
 */
@Service("dealerApiService")
public class DealerApiServiceImpl implements DealerApiService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResultEntity getUserInfo(long userId) {
        UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(userId);
        if (userInfo == null) {
            return ResultEntityUtil.err("null");
        }
        ResultEntity resultEntity = ResultEntityUtil.ok();
        resultEntity.add("userid", userInfo.getUserId());
        resultEntity.add("name", userInfo.getName());
        resultEntity.add("gems", userInfo.getGems());
        resultEntity.add("headimg", userInfo.getHeadImg());
        return resultEntity;
    }

    @Override
    public ResultEntity addUserGems(long userId, int gems) {
        UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(userId);
        if (userInfo == null || gems <= 0 || gems >= Constant.ONE_TIME_CAN_BUY_GEMS_NUM) {
            return ResultEntityUtil.err("failed");
        }
        userInfo.setGems(userInfo.getGems() + gems);
        this.userInfoMapper.updateByPrimaryKey(userInfo);
        return ResultEntityUtil.ok();
    }

}
