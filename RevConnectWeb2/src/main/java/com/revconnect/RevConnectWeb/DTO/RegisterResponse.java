package com.revconnect.RevConnectWeb.DTO;


import com.revconnect.RevConnectWeb.entity.AccountType;

public class RegisterResponse {

    private String email;
    private String username;
    private AccountType accountType;
    private String message;

    public RegisterResponse(String email, String username, AccountType accountType, String message) {
        this.email = email;
        this.username = username;
        this.accountType = accountType;
        this.message = message;
    }

    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public AccountType getAccountType() { return accountType; }
    public String getMessage() { return message; }
}