package com.liubowen.socketiomahjong.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author liubowen
 * @date 2017/11/10 1:53
 * @description
 */
@SpringBootConfiguration
public class SocketIoConfig {

    @Value("${netty.socketio.host}")
    private String host;

    @Value("${netty.socketio.port}")
    private int port;

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setHostname(host);
        configuration.setPort(port);
        return configuration;
    }

    @Bean
    public SocketIOServer getSocketIOServer() {
        SocketIOServer socketIOServer = new SocketIOServer(getConfiguration());
        return socketIOServer;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(getSocketIOServer());
    }

}
