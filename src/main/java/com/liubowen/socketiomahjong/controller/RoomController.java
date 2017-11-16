package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.common.ResultEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author liubowen
 * @date 2017/11/10 19:53
 * @description
 */
@RestController
public class RoomController {


    @GetMapping("/register_gs")
    public Map<String, Object> registerGs(@RequestParam("account") String account,
                                          @RequestParam("clientip") String clientip,
                                          @RequestParam("clientport") String clientport,
                                          @RequestParam("httpPort") String httpPort,
                                          @RequestParam("load") String load,
                                          @RequestParam("id") String id) {
        return null;
    }
}
