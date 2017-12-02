package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.service.DealerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<String, Object> getUserInfo(@RequestParam("userid") long userId) {
        return this.dealerApiService.getUserInfo(userId).result();
    }

    @GetMapping("/add_user_gems")
    public Map<String, Object> addUserGems(@RequestParam("userid") long userId, @RequestParam("gems") int gems) {
        return this.dealerApiService.addUserGems(userId, gems).result();
    }

}
