package com.example.livemessaging.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginServiceTest {

    private final LoginService loginService = new LoginService();

    @Test
    void createsValidAdminTokenForCorrectCredentials() {
        LoginService.LoginResult result = loginService.login("admin", "admin123").orElseThrow();

        assertEquals("ADMIN", result.role());
        assertTrue(loginService.isValidToken(result.token()));
        assertEquals("admin", loginService.usernameForToken(result.token()).orElseThrow());
    }

    @Test
    void rejectsIncorrectCredentialsAndUnknownTokens() {
        assertTrue(loginService.login("binh", "wrong").isEmpty());
        assertFalse(loginService.isValidToken("not-a-token"));
        assertFalse(loginService.isValidToken(null));
    }
}
