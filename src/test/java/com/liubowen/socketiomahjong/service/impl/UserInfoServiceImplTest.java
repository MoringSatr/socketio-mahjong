package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/9 21:56
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void save() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("11");
        userService.save(userInfo);
    }

    @Test
    public void all() throws Exception {
        List<UserInfo> list = userService.all();
        list.forEach(System.err :: println);
    }

    @Test
    public void findOne() throws Exception {
        UserInfo userInfo = new UserInfo();
        UserInfo result = userService.findOne(userInfo);
        System.err.println(result);
    }

    @Test
    public void findList() throws Exception {
        UserInfo userInfo = new UserInfo();
        List<UserInfo> list = userService.findList(userInfo);
        list.forEach(System.err :: println);
    }

    @Test
    public void delete() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("11");
        userService.delete(userInfo);
        List<UserInfo> list = userService.all();
        list.forEach(System.err :: println);
    }

    @Test
    public void update() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("11");
        userService.update(userInfo);
        List<UserInfo> list = userService.all();
        list.forEach(System.err :: println);
    }

}