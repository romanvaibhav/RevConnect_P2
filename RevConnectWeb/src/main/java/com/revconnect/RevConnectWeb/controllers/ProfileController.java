package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.ProfileDTO;
import com.revconnect.RevConnectWeb.entity.Profiles;
import com.revconnect.RevConnectWeb.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService){
        this.profileService=profileService;
    }

    @PostMapping("/profile/{userId}")
    public ProfileDTO userProfile(@PathVariable Long userId, @RequestBody ProfileDTO profileDTO ){
        System.out.println("DTO name = " + profileDTO.getName());
//        ProfileDTO savedProfile=
        return profileService.createProfile(userId,profileDTO);
    }

    @PatchMapping("/updprofile/{userId}")
    public ProfileDTO updateUserProfile(@PathVariable Long userId, @RequestBody ProfileDTO profileDTO){
        return profileService.updateProfile(userId,profileDTO);
    }

    @GetMapping("userProfile/{userId}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long userId) {
        ProfileDTO profile = profileService.getProfile(userId);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/searchUser")
    public List<ProfileDTO> searchUsers(@RequestParam String name){
        System.out.println(name);
        return profileService.searchUserProfile(name);
    }
}
