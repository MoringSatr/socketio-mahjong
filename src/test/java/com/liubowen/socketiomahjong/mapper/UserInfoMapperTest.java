package com.liubowen.socketiomahjong.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liubowen
 * @date 2017/11/29 10:55
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserInfoMapperTest {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Test
    public void isUserExistByAccount() throws Exception {
        log.info("result : " + this.accountInfoMapper.isUserExist("11"));
    }

}