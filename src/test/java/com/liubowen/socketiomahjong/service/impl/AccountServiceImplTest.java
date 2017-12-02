package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liubowen
 * @date 2017/11/29 11:14
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void register() throws Exception {
        ResultEntity resultEntity = accountService.register("11", "22");
        log.info(resultEntity.result().toString());
    }

    @Test
    public void getVersion() throws Exception {
    }

    @Test
    public void getServerinfo() throws Exception {
    }

    @Test
    public void guest() throws Exception {
    }

    @Test
    public void auth() throws Exception {
    }

    @Test
    public void wechatAuth() throws Exception {
    }

    @Test
    public void baseInfo() throws Exception {
    }

}