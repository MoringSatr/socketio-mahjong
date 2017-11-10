package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.common.ResultEntity;

/**
 * @author liubowen
 * @date 2017/11/10 2:53
 * @description 帐号业务处理
 */
public interface AccountService {

    ResultEntity register(String account, String password);

    ResultEntity getVersion();

    ResultEntity getServerinfo();

    ResultEntity guest(String account);

    ResultEntity auth(String account, String password);

    ResultEntity wechatAuth(String code, String os);

    ResultEntity baseInfo(String userid);

}
