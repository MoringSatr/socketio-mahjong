package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.service.DealerApiService;
import com.liubowen.socketiomahjong.util.result.ResultEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liubowen
 * @date 2017/11/10 19:36
 * @description
 */
@RestController
public class DealerApiController {

    @Autowired
    private DealerApiService dealerApiService;

    @GetMapping("/get_user_info")
    public ResultEntity getUserInfo(@RequestParam("userid") String userid) {
        return ResultEntityUtil.ok();
    }

    @GetMapping("/add_user_gems")
    public ResultEntity addUserGems(@RequestParam("userid") String userid,
                                    @RequestParam("gems") String gems) {
        return ResultEntityUtil.ok();
    }

}
