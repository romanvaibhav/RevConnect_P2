package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.AuthResponse;
import com.revconnect.RevConnectWeb.entity.AccountType;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

//    private  final PasswordEncoder passwordEncoder;

//    PasswordEncoder passwordEncoder
    public AuthService(UserRepository userRepository){
        this.userRepository=userRepository;
//        this.passwordEncoder=passwordEncoder;
    }

    public String register(String email, String username, String password, AccountType accountType){

        if(userRepository.existsByEmail(email)) throw new RuntimeException("Email already exists");

        if(userRepository.existsByUsername(username)) throw new RuntimeException("Username alredy exists");

        if(accountType == null)
            accountType=AccountType.PERSONAL;

//        String passwordHash= passwordEncoder.encode(password);

        User user=new User(email,username,password,accountType);

        userRepository.save(user);
        return "Registered Succesfully";

    }

    public AuthResponse login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
//            throw new RuntimeException("Invalid credentials");
//        }

        return new AuthResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAccountType()
        );
    }
}
