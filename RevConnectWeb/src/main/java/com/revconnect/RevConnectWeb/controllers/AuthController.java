package com.revconnect.RevConnectWeb.controllers;


import com.revconnect.RevConnectWeb.DTO.AuthResponse;
import com.revconnect.RevConnectWeb.entity.AccountType;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> body) {
        System.out.println("REGISTER HIT");

        String email = (String) body.get("email");
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        AccountType accountType = AccountType.valueOf((String) body.get("accountType"));


        return ResponseEntity.ok(
                authService.register(
                        email,
                        username,
                        password,
                        accountType
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Map<String, Object> body){

        String email = (String) body.get("email");
        String password = (String) body.get("password");

        return ResponseEntity.ok(authService.login(email,password));
    }
}
