package com.example.livemessaging.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.livemessaging.auth.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration
public class SocketIoConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SocketIOServer socketIOServer(
            LoginService loginService,
            @Value("${app.socket.port:9092}") int socketPort) {
        Configuration configuration = new Configuration();
        configuration.setHostname("0.0.0.0");
        configuration.setPort(socketPort);
        configuration.setOrigin("*");
        configuration.setAuthorizationListener(handshakeData ->
                loginService.isValidToken(handshakeData.getSingleUrlParam("token"))
                        ? AuthorizationResult.SUCCESSFUL_AUTHORIZATION
                        : AuthorizationResult.FAILED_AUTHORIZATION);
        return new SocketIOServer(configuration);
    }
}
