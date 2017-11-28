package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.service.AccountService;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import org.springframework.stereotype.Service;

/**
 * @author liubowen
 * @date 2017/11/10 2:53
 * @description
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Override
    public ResultEntity register(String account, String password) {
        return ResultEntityUtil.ok();
    }

    @Override
    public ResultEntity getVersion() {
        return null;
    }

    @Override
    public ResultEntity getServerinfo() {
        return null;
    }

    @Override
    public ResultEntity guest(String account) {
        return null;
    }

    @Override
    public ResultEntity auth(String account, String password) {
        return null;
    }

    @Override
    public ResultEntity wechatAuth(String code, String os) {
        return null;
    }

    @Override
    public ResultEntity baseInfo(String userid) {
        return null;
    }
}
