package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam("account") String account,
                                     @RequestParam("sign") String sign) {
        return this.clientService.login(account, sign).result();
    }

    @GetMapping("/create_user")
    public Map<String, Object> createUser(@RequestParam("account") String account,
                                   @RequestParam("sign") String sign,
                                   @RequestParam("name") String name) {
        return this.clientService.createUser(account, sign, name).result();
    }

    @GetMapping("/create_private_room")
    public Map<String, Object> createPrivateRoom(@RequestParam("account") String account,
                                          @RequestParam("sign") String sign,
                                          @RequestParam("userid") String userid,
                                          @RequestParam("name") String name) {
        return this.clientService.createPrivateRoom(account, sign, userid, name).result();
    }

    @GetMapping("/enter_private_room")
    public Map<String, Object> enterPrivateRoom(@RequestParam("roomid") String roomid,
                                         @RequestParam("account") String account,
                                         @RequestParam("userid") String userid,
                                         @RequestParam("name") String name) {
        return this.clientService.enterPrivateRoom(roomid, account, userid, name).result();
    }

    @GetMapping("/get_history_list")
    public Map<String, Object> getHistoryList(@RequestParam("account") String account,
                                       @RequestParam("sign") String sign,
                                       @RequestParam("userid") String userid) {
        return this.clientService.getHistoryList(account, sign, userid).result();
    }

    @GetMapping("/get_games_of_room")
    public Map<String, Object> getGamesOfRoom(@RequestParam("uuid") String uuid) {
        return this.clientService.getGamesOfRoom(uuid).result();
    }

    @GetMapping("/get_detail_of_game")
    public Map<String, Object> getDetailOfGame(@RequestParam("account") String account,
                                        @RequestParam("sign") String sign,
                                        @RequestParam("uuid") String uuid,
                                        @RequestParam("index") String index) {
        return this.clientService.getDetailOfGame(account, sign, uuid, index).result();
    }

    @GetMapping("/get_user_status")
    public Map<String, Object> getUserStatus(@RequestParam("account") String account,
                                      @RequestParam("sign") String sign) {
        return this.clientService.getUserStatus(account, sign).result();
    }

    @GetMapping("/get_message")
    public Map<String, Object> getMessage(@RequestParam("account") String account,
                                   @RequestParam("sign") String sign,
                                   @RequestParam("type") String type,
                                   @RequestParam("version") String version) {
        return this.clientService.getMessage(account, sign, type, version).result();
    }

    @GetMapping("/is_server_online")
    public Map<String, Object> isServerOnline(@RequestParam("account") String account,
                                       @RequestParam("sign") String sign,
                                       @RequestParam("ip") String ip,
                                       @RequestParam("port") String port) {
        return this.clientService.isServerOnline(account, sign, ip, port).result();
    }


}
