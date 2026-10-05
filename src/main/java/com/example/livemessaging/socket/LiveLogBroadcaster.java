package com.example.livemessaging.socket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class LiveLogBroadcaster {

    private final SocketIOServer server;

    public LiveLogBroadcaster(SocketIOServer server) {
        this.server = server;
    }

    public void broadcastAdminLogin() {
        server.getBroadcastOperations().sendEvent("log", Map.of(
                "message", "Admin đã đăng nhập",
                "time", Instant.now().toString()
        ));
    }
}
