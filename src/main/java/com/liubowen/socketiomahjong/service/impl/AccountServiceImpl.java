package com.liubowen.socketiomahjong.service.impl;

import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.constant.AppInfo;
import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.entity.AccountInfo;
import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.AccountInfoMapper;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.service.AccountService;
import com.liubowen.socketiomahjong.service.UserDomainService;
import com.liubowen.socketiomahjong.util.encode.Md5Util;
import com.liubowen.socketiomahjong.util.http.HttpUtil;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/11/10 2:53
 * @description
 */
@Service("accountService")
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserDomainService userDomainService;

    @Override
    public ResultEntity register(String account, String password) {
        if (StringUtils.isEmpty(account)) {
            return ResultEntityUtil.err("account is empty.");
        }
        if (StringUtils.isEmpty(password)) {
            return ResultEntityUtil.err("password is empty.");
        }
        // 在数据库查询用户是否存在
        boolean isUserExist = this.userInfoMapper.isUserExistByAccount(account);
        if (isUserExist) {
            return ResultEntityUtil.err("account has been used.");
        } else {
            AccountInfo accountInfo = new AccountInfo(account, Md5Util.MD5(password));
            this.accountInfoMapper.insertSelective(accountInfo);
            log.info(accountInfo.toString());
            UserInfo userInfo = this.userDomainService.createUser(account, "用户_" + account, RandomUtils.nextInt(0, 1), Constant.randomHeadImage(), 1000, 21);
            log.info(userInfo.toString());
            return ResultEntityUtil.ok();
        }
    }

    @Override
    public ResultEntity getVersion() {
        ResultEntity resultEntity = ResultEntityUtil.empty();
        resultEntity.add("version", Constant.VERSION);
        return resultEntity;
    }

    @Override
    public ResultEntity getServerinfo() {
        ResultEntity resultEntity = ResultEntityUtil.empty();
        resultEntity.add("version", Constant.VERSION);
        resultEntity.add("hall", Constant.HALL_ADDR);
        resultEntity.add("appweb", Constant.APP_WEB);
        return resultEntity;
    }

    @Override
    public ResultEntity guest(String account, HttpServletRequest request) {
        ResultEntity resultEntity = ResultEntityUtil.ok();
        String reqIp = Constant.getIpByRequest(request);
        String sign = Md5Util.MD5(account + reqIp + Constant.ACCOUNT_PRI_KEY);
        resultEntity.add("account", account);
        resultEntity.add("halladdr", Constant.HALL_ADDR);
        resultEntity.add("sign", sign);
        return resultEntity;
    }

    @Override
    public ResultEntity auth(String account, String password, HttpServletRequest request) {
        AccountInfo accountInfo = this.accountInfoMapper.findAccountInfoByAccountAndPassword(account, Md5Util.MD5(password));
        if (accountInfo == null) {
            return ResultEntityUtil.err("invalid account");
        }
        ResultEntity resultEntity = ResultEntityUtil.ok();
        String reqIp = Constant.getIpByRequest(request);
        String sign = Md5Util.MD5("vivi_" + account + reqIp + Constant.ACCOUNT_PRI_KEY);
        resultEntity.add("account", account);
        resultEntity.add("sign", sign);
        return resultEntity;
    }

    @Override
    public ResultEntity wechatAuth(String code, String os, HttpServletRequest request) {
        if (code == null || code == "" || os == null || os == "") {
            return ResultEntityUtil.err("prarms is error");
        }
        log.info("os : {}", os);
        AppInfo appInfo = AppInfo.valueOf(os);
        if (appInfo != null) {
            JSONObject jsonObject = this.getAccessToken(appInfo.getAppid(), appInfo.getSecret(), code, "authorization_code");
            if (jsonObject != null) {
                String accessToken = (String) jsonObject.get("access_token");
                String openId = (String) jsonObject.get("openid");
                JSONObject stateInfo = this.getStateInfo(accessToken, openId);
                String openid = (String) stateInfo.get("openid");
                String nickname = (String) stateInfo.get("nickname");
                int sex = (Integer) stateInfo.get("sex");
                String headimgurl = (String) stateInfo.get("headimgurl");
                String account = "wx_" + openid;
                UserInfo userInfo = this.userDomainService.createUser(account, nickname, sex, headimgurl, 1000, 21);
                ResultEntity resultEntity = ResultEntityUtil.ok();
                String reqIp = Constant.getIpByRequest(request);
                String sign = Md5Util.MD5(account + reqIp + Constant.ACCOUNT_PRI_KEY);
                resultEntity.add("account", account);
                resultEntity.add("halladdr", Constant.HALL_ADDR);
                resultEntity.add("sign", sign);
            }
        }
        return ResultEntityUtil.unknowErr();
    }

    @Override
    public ResultEntity baseInfo(long userid) {
        UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(userid);
        if (userInfo == null) {
            return ResultEntityUtil.err("user is not exist");
        }
        ResultEntity resultEntity = ResultEntityUtil.ok();
        resultEntity.add("name", userInfo.getName());
        resultEntity.add("sex", userInfo.getSex());
        resultEntity.add("headimgurl", userInfo.getHeadImg());
        return resultEntity;
    }

    private JSONObject getAccessToken(String appid, String secret, String code, String grantType) {
        String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String, Object> params = Maps.newHashMap();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("code", code);
        params.put("grant_type", grantType);
        String result = HttpUtil.sendGet(url, params);
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

    public JSONObject getStateInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String, Object> params = Maps.newHashMap();
        params.put("access_token", accessToken);
        params.put("openid", openId);
        String result = HttpUtil.sendGet(url, params);
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

}
