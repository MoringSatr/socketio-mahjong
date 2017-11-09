package com.liubowen.socketiomahjong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "安全登录校验controller", description = "密码错误次数校验", tags = {"安全登录校验接口"})
@RestController
public class SecurityLoginCheckController {

    @ApiOperation("密码错误次数校验")
    @PostMapping("/checkPassword")
    public String checkPasswordErrorFrequency(String username, String password) {
        // TODO: 2017/9/30 具体的业务逻辑
        return "ok";
    }

}
