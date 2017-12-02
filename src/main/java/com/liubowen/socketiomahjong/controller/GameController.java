package com.liubowen.socketiomahjong.controller;

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
public class GameController {

    @GetMapping("/get_server_info")
    public Map<String, Object> getServerInfo(@RequestParam("serverId") String serverId, @RequestParam("sign") String sign) {
        return null;
    }

    @GetMapping("/create_room")
    public Map<String, Object> create_room(@RequestParam("serverId") String serverId, @RequestParam("sign") String sign) {
        return null;
    }

    @GetMapping("/enter_room")
    public Map<String, Object> enter_room(@RequestParam("serverId") String serverId, @RequestParam("sign") String sign) {
        return null;
    }

    @GetMapping("/ping")
    public Map<String, Object> ping(@RequestParam("serverId") String serverId, @RequestParam("sign") String sign) {
        return null;
    }

    @GetMapping("/is_room_runing")
    public Map<String, Object> is_room_runing(@RequestParam("serverId") String serverId, @RequestParam("sign") String sign) {
        return null;
    }

}
