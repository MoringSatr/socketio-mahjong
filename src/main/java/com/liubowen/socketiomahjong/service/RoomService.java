package com.liubowen.socketiomahjong.service;

import com.liubowen.socketiomahjong.common.ResultEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liubowen
 * @date 2017/12/13 10:12
 * @description
 */
public interface RoomService {

    ResultEntity registerGs(String account, String clientip, int clientport, int httpPort, String load, HttpServletRequest request);
}
