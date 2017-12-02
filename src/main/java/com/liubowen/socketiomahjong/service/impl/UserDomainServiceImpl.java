package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubowen
 * @date 2017/11/29 14:00
 * @description
 */
@Service("userDomainService")
public class UserDomainServiceImpl implements UserDomainService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo createUser(String account, String name, int sex, String headImg, int coins, int gems) {
        UserInfo userInfo = new UserInfo(account, name, sex, headImg, coins, gems);
        this.userInfoMapper.insertSelective(userInfo);
        return userInfo;
    }
}
