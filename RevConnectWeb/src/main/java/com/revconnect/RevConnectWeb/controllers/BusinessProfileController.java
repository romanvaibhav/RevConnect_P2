package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.BusinessProfileDTO;
import com.revconnect.RevConnectWeb.entity.BusinessProfile;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.BusinessProfileRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/businessProfile")
public class BusinessProfileController {

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.revconnect.RevConnectWeb.controller.BusinessProfileService businessProfileService;

    // 1. Create Profile
    @PostMapping("/create")
    public ResponseEntity<String> createProfile(@RequestBody BusinessProfileDTO businessProfileDTO) {
        businessProfileService.createOrUpdateProfile(businessProfileDTO);
        return ResponseEntity.ok("Business Profile created successfully.");
    }

    // 2. Update Profile
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody BusinessProfileDTO businessProfileDTO) {
        businessProfileDTO.setUserId(userId);
        businessProfileService.createOrUpdateProfile(businessProfileDTO);
        return ResponseEntity.ok("Business Profile updated successfully.");
    }

    // 3. View Profile by userId
    @GetMapping("/view/{userId}")
    public ResponseEntity<BusinessProfileDTO> viewProfile(@PathVariable Long userId) {
        BusinessProfileDTO profileDTO = businessProfileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profileDTO);
    }
}