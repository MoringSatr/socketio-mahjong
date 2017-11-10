package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.entity.UserInfo;
import com.liubowen.socketiomahjong.mapper.UserInfoMapper;
import com.liubowen.socketiomahjong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/9 21:48
 * @description
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean save(UserInfo userInfo) {
        return this.userInfoMapper.insert(userInfo) == 1;
    }

    @Override
    public List<UserInfo> all() {
        return this.userInfoMapper.selectAll();
    }

    @Override
    public UserInfo findOne(UserInfo userInfo) {
        return this.userInfoMapper.selectOne(userInfo);
    }

    @Override
    public List<UserInfo> findList(UserInfo userInfo) {
        return this.userInfoMapper.select(userInfo);
    }

    @Override
    public boolean delete(UserInfo userInfo) {
        return this.userInfoMapper.delete(userInfo) == 1;
    }

    @Override
    public boolean update(UserInfo userInfo) {
        return this.userInfoMapper.updateByPrimaryKeySelective(userInfo) == 1;
    }
}
