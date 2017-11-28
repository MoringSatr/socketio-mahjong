package com.liubowen.socketiomahjong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author liubowen
 * @date 2017/11/28 17:48
 * @description
 */
@Slf4j
public class Bootstrap implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("server start ...");
    }
}
