package com.liubowen.socketiomahjong.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liubowen
 * @date 2017/11/10 4:25
 * @description
 */
@Component("messageSender")
public class MessageSender {

    @Autowired
    private MessageBuilder messageBuilder;

}
