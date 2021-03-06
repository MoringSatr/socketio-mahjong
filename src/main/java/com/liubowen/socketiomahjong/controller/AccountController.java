package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    public Map<String, Object> register(@RequestParam("account") String account,
                                 @RequestParam("password") String password) {
        return this.accountService.register(account, password).result();
    }

    @GetMapping("/get_version")
    public Map<String, Object> getVersion() {
        return this.accountService.getVersion().result();
    }

    @GetMapping("/get_serverinfo")
    public Map<String, Object> getServerinfo() {
        return this.accountService.getServerinfo().result();
    }

    @GetMapping("/guest")
    public Map<String, Object> guest(@RequestParam("account") String account, HttpServletRequest request) {
        return this.accountService.guest(account, request).result();
    }

    @GetMapping("/auth")
    public Map<String, Object> auth(@RequestParam("account") String account,
            @RequestParam("password") String password, HttpServletRequest request) {
        return this.accountService.auth(account, password, request).result();
    }

    @GetMapping("/wechat_auth")
    public Map<String, Object> wechatAuth(@RequestParam("code") String code,
            @RequestParam("os") String os, HttpServletRequest request) {
        return this.accountService.wechatAuth(code, os, request).result();
    }

    @GetMapping("/base_info")
    public Map<String, Object> baseInfo(@RequestParam("userid") long userid) {
        return this.accountService.baseInfo(userid).result();
    }

}
