package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liubowen
 * @date 2017/11/10 2:54
 * @description 帐号控制器
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public ResultEntity register(@RequestParam("account") String account,
                                 @RequestParam("password") String password) {
        return this.accountService.register(account, password);
    }

    @GetMapping("/get_version")
    public ResultEntity getVersion() {
        return this.accountService.getVersion();
    }

    @GetMapping("/get_serverinfo")
    public ResultEntity getServerinfo() {
        return this.accountService.getServerinfo();
    }

    @GetMapping("/guest")
    public ResultEntity guest(@RequestParam("account") String account) {
        return this.accountService.guest(account);
    }

    @GetMapping("/auth")
    public ResultEntity auth(@RequestParam("account") String account,
                                 @RequestParam("password") String password) {
        return this.accountService.auth(account, password);
    }

    @GetMapping("/wechat_auth")
    public ResultEntity wechatAuth(@RequestParam("code") String code,
                                 @RequestParam("os") String os) {
        return this.accountService.wechatAuth(code, os);
    }

    @GetMapping("/base_info")
    public ResultEntity baseInfo(@RequestParam("userid") String userid) {
        return this.accountService.baseInfo(userid);
    }

}
