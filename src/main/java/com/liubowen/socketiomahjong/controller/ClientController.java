package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResultEntity login(@RequestParam("account") String account,
                              @RequestParam("sign") String sign) {
        return this.clientService.login(account, sign);
    }

    @GetMapping("/create_user")
    public ResultEntity createUser(@RequestParam("account") String account,
                                   @RequestParam("sign") String sign,
                                   @RequestParam("name") String name) {
        return this.clientService.createUser(account, sign, name);
    }

    @GetMapping("/create_private_room")
    public ResultEntity createPrivateRoom(@RequestParam("account") String account,
                                          @RequestParam("sign") String sign,
                                          @RequestParam("userid") String userid,
                                          @RequestParam("name") String name) {
        return this.clientService.createPrivateRoom(account, sign, userid, name);
    }

    @GetMapping("/enter_private_room")
    public ResultEntity enterPrivateRoom(@RequestParam("roomid") String roomid,
                                         @RequestParam("account") String account,
                                         @RequestParam("userid") String userid,
                                         @RequestParam("name") String name) {
        return this.clientService.enterPrivateRoom(roomid, account, userid, name);
    }

    @GetMapping("/get_history_list")
    public ResultEntity getHistoryList(@RequestParam("account") String account,
                                       @RequestParam("sign") String sign,
                                       @RequestParam("userid") String userid) {
        return this.clientService.getHistoryList(account, sign, userid);
    }

    @GetMapping("/get_games_of_room")
    public ResultEntity getGamesOfRoom(@RequestParam("uuid") String uuid) {
        return this.clientService.getGamesOfRoom(uuid);
    }

    @GetMapping("/get_detail_of_game")
    public ResultEntity getDetailOfGame(@RequestParam("account") String account,
                                        @RequestParam("sign") String sign,
                                        @RequestParam("uuid") String uuid,
                                        @RequestParam("index") String index) {
        return this.clientService.getDetailOfGame(account, sign, uuid, index);
    }

    @GetMapping("/get_user_status")
    public ResultEntity getUserStatus(@RequestParam("account") String account,
                                      @RequestParam("sign") String sign) {
        return this.clientService.getUserStatus(account, sign);
    }

    @GetMapping("/get_message")
    public ResultEntity getMessage(@RequestParam("account") String account,
                                   @RequestParam("sign") String sign,
                                   @RequestParam("type") String type,
                                   @RequestParam("version") String version) {
        return this.clientService.getMessage(account, sign, type, version);
    }

    @GetMapping("/is_server_online")
    public ResultEntity isServerOnline(@RequestParam("account") String account,
                                       @RequestParam("sign") String sign,
                                       @RequestParam("ip") String ip,
                                       @RequestParam("port") String port) {
        return this.clientService.isServerOnline(account, sign, ip, port);
    }


}
