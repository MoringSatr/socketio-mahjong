package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.entity.UserInfo;

/**
 * @author liubowen
 * @date 2017/11/9 21:47
 * @description
 */
public interface UserDomainService {

    UserInfo createUser(String account, String name, int sex, String headImg, int coins, int gems);
}
