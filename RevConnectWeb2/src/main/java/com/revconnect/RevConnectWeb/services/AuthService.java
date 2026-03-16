package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.AuthResponse;
import com.revconnect.RevConnectWeb.entity.AccountType;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.revconnect.RevConnectWeb.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public String register(String email, String username, String password, AccountType accountType){

        if(userRepository.existsByEmail(email)) throw new RuntimeException("Email already exists");

        if(userRepository.existsByUsername(username)) throw new RuntimeException("Username alredy exists");

        if(accountType == null)
            accountType=AccountType.PERSONAL;

        String passwordHash= passwordEncoder.encode(password);

        User user=new User(email,username,passwordHash,accountType);

        userRepository.save(user);
        return "Registered Succesfully";

    }

    public AuthResponse login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String token = jwtService.generateToken(email);

        return new AuthResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAccountType(),
                token
        );
    }
}
