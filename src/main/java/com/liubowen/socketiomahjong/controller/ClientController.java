package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
            @RequestParam("sign") String sign, HttpServletRequest request) {
        return this.clientService.login(account, sign, request).result();
    }

    @GetMapping("/create_user")
    public Map<String, Object> createUser(@RequestParam("account") String account,
                                   @RequestParam("sign") String sign,
            @RequestParam("name") String name, HttpServletRequest request) {
        return this.clientService.createUser(account, sign, name, request).result();
    }

    @GetMapping("/create_private_room")
    public Map<String, Object> createPrivateRoom(@RequestParam("account") String account,
                                          @RequestParam("sign") String sign,
            @RequestParam("userid") long userId, @RequestParam("name") String name, HttpServletRequest request) {
        return this.clientService.createPrivateRoom(account, sign, userId, name, request).result();
    }

    @GetMapping("/enter_private_room")
    public Map<String, Object> enterPrivateRoom(@RequestParam("roomid") String roomId,
                                         @RequestParam("account") String account,
            @RequestParam("userid") long userId, @RequestParam("name") String name, HttpServletRequest request) {
        return this.clientService.enterPrivateRoom(roomId, account, userId, name, request).result();
    }

    @GetMapping("/get_history_list")
    public Map<String, Object> getHistoryList(@RequestParam("account") String account,
                                       @RequestParam("sign") String sign,
            @RequestParam("userid") long userId, HttpServletRequest request) {
        return this.clientService.getHistoryList(account, sign, userId, request).result();
    }

    @GetMapping("/get_games_of_room")
    public Map<String, Object> getGamesOfRoom(@RequestParam("uuid") String uuid, HttpServletRequest request) {
        return this.clientService.getGamesOfRoom(uuid, request).result();
    }

    @GetMapping("/get_detail_of_game")
    public Map<String, Object> getDetailOfGame(@RequestParam("account") String account,
                                        @RequestParam("sign") String sign,
                                        @RequestParam("uuid") String uuid,
            @RequestParam("index") int index, HttpServletRequest request) {
        return this.clientService.getDetailOfGame(account, sign, uuid, index, request).result();
    }

    @GetMapping("/get_user_status")
    public Map<String, Object> getUserStatus(@RequestParam("account") String account,
            @RequestParam("sign") String sign, HttpServletRequest request) {
        return this.clientService.getUserStatus(account, sign, request).result();
    }

    @GetMapping("/get_message")
    public Map<String, Object> getMessage(@RequestParam("account") String account,
                                   @RequestParam("sign") String sign,
                                   @RequestParam("type") String type,
            @RequestParam("version") String version, HttpServletRequest request) {
        return this.clientService.getMessage(account, sign, type, version, request).result();
    }

    @GetMapping("/is_server_online")
    public Map<String, Object> isServerOnline(@RequestParam("account") String account,
                                       @RequestParam("sign") String sign,
                                       @RequestParam("ip") String ip,
            @RequestParam("port") int port, HttpServletRequest request) {
        return this.clientService.isServerOnline(account, sign, ip, port, request).result();
    }


}
