package com.liubowen.socketiomahjong.service.impl;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.service.DealerApiService;
import org.springframework.stereotype.Service;

/**
 * @author liubowen
 * @date 2017/11/10 19:39
 * @description
 */
@Service("dealerApiService")
public class DealerApiServiceImpl implements DealerApiService {

    @Override
    public ResultEntity getUserInfo(String userid) {
        return null;
    }

    @Override
    public ResultEntity addUserGems(String userid, String gems) {
        return null;
    }

}
