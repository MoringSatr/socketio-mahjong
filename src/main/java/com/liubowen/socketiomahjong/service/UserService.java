package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.entity.UserInfo;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/9 21:47
 * @description
 */
public interface UserService {

    boolean save(UserInfo userInfo);

    List<UserInfo> all();

    UserInfo findOne(UserInfo userInfo);

    List<UserInfo> findList(UserInfo userInfo);

    boolean delete(UserInfo userInfo);

    boolean update(UserInfo userInfo);
}

