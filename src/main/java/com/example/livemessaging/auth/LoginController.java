package com.example.livemessaging.auth;

import com.example.livemessaging.socket.LiveLogBroadcaster;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final LiveLogBroadcaster liveLogBroadcaster;

    public LoginController(LoginService loginService, LiveLogBroadcaster liveLogBroadcaster) {
        this.loginService = loginService;
        this.liveLogBroadcaster = liveLogBroadcaster;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request == null || request.username() == null || request.password() == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("username and password are required"));
        }

        var result = loginService.login(request.username(), request.password());
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid username or password"));
        }

        LoginService.LoginResult loginResult = result.orElseThrow();
        if ("ADMIN".equals(loginResult.role())) {
            liveLogBroadcaster.broadcastAdminLogin();
        }
        return ResponseEntity.ok(loginResult);
    }

    public record LoginRequest(String username, String password) {
    }

    public record ErrorResponse(String error) {
    }
}
