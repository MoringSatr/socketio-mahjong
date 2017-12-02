package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.common.ResultEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liubowen
 * @date 2017/11/10 2:53
 * @description 帐号业务处理
 */
public interface AccountService {

    ResultEntity register(String account, String password);

    ResultEntity getVersion();

    ResultEntity getServerinfo();

    ResultEntity guest(String account, HttpServletRequest request);

    ResultEntity auth(String account, String password, HttpServletRequest request);

    ResultEntity wechatAuth(String code, String os, HttpServletRequest request);

    ResultEntity baseInfo(long userid);

}
