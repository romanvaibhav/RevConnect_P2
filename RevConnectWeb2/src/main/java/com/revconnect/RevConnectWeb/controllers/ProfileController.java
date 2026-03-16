package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.ProfileDTO;
import com.revconnect.RevConnectWeb.entity.Profiles;
import com.revconnect.RevConnectWeb.services.ProfileService;
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
    public  ProfileDTO getProfile(@PathVariable Long userId){
        return profileService.getProfile(userId);
    }

    @GetMapping("/searchUser")
    public List<ProfileDTO> searchUsers(@RequestParam String name){
        System.out.println(name);
        return profileService.searchUserProfile(name);
    }
}
