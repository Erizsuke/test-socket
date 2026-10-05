package com.example.livemessaging.auth;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginService {

    private static final Map<String, Account> ACCOUNTS = Map.of(
            "admin", new Account("admin123", "ADMIN"),
            "binh", new Account("binh123", "USER")
    );

    private final Set<String> validTokens = ConcurrentHashMap.newKeySet();
    private final Map<String, String> tokenOwners = new ConcurrentHashMap<>();

    public Optional<LoginResult> login(String username, String password) {
        Account account = ACCOUNTS.get(username);
        if (account == null || !account.password().equals(password)) {
            return Optional.empty();
        }

        String token = UUID.randomUUID().toString();
        validTokens.add(token);
        tokenOwners.put(token, username);
        return Optional.of(new LoginResult(token, username, account.role()));
    }

    public boolean isValidToken(String token) {
        return token != null && validTokens.contains(token);
    }

    public Optional<String> usernameForToken(String token) {
        return Optional.ofNullable(tokenOwners.get(token));
    }

    private record Account(String password, String role) {
    }

    public record LoginResult(String token, String username, String role) {
    }
}
