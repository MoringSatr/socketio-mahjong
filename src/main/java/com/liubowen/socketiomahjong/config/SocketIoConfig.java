package com.liubowen.socketiomahjong.config;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
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
        // configuration.setOrigin("*");
        // 该处可以用来进行身份验证
        configuration.setAuthorizationListener(new SocketIoAuthorizationListener());
        return configuration;
    }

    @Bean("socketIOServer")
    public SocketIOServer getSocketIOServer() {
        SocketIOServer socketIOServer = new SocketIOServer(getConfiguration());
        return socketIOServer;
    }

    /** 身份验证监听 */
    private class SocketIoAuthorizationListener implements AuthorizationListener {
        @Override
        public boolean isAuthorized(HandshakeData data) {
            // http://localhost:8081?username=test&password=test
            // 例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
            // String username = data.getSingleUrlParam("username");
            // String password = data.getSingleUrlParam("password");
            return true;
        }
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(getSocketIOServer());
    }

}
