package com.liubowen.socketiomahjong.service.impl;

import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.common.ResultEntity;
import com.liubowen.socketiomahjong.common.ServerInfo;
import com.liubowen.socketiomahjong.constant.Constant;
import com.liubowen.socketiomahjong.service.RoomService;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentMap;

/**
 * @author liubowen
 * @date 2017/12/13 10:12
 * @description
 */
public class RoomServiceImpl implements RoomService {

    private ConcurrentMap<String, ServerInfo> serverMap = Maps.newConcurrentMap();

    @Override
    public ResultEntity registerGs(String account, String clientip, int clientport, int httpPort, String load, HttpServletRequest request) {
        String ip = Constant.getIpByRequest(request);
        String id = clientip + ":" + clientport;
        if(this.serverMap.containsKey(id)) {
            ServerInfo serverInfo = this.serverMap.get(id);

        }
        return null;
    }
}
