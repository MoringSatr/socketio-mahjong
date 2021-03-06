package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.common.ResultEntity;

/**
 * @author liubowen
 * @date 2017/11/10 19:38
 * @description
 */
public interface DealerApiService {

    ResultEntity getUserInfo(long userId);

    ResultEntity addUserGems(long userId, int gems);
}
