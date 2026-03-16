package com.revconnect.RevConnectWeb.DTO;

import com.revconnect.RevConnectWeb.entity.AccountType;

public class AuthResponse {
    private Long userId;

    private String username;
    private String token;
    private String tokenType = "Bearer";

    public AuthResponse(String token) { this.token = token; }

    public String getToken() { return token; }
    public String getTokenType() { return tokenType; }
    public void setToken(String token) { this.token = token; }
    private String email;

    private AccountType accountType;

    public AuthResponse(){}

    public AuthResponse(Long userId, String username, String email, AccountType accountType,String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.accountType = accountType;
        this.token=token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
