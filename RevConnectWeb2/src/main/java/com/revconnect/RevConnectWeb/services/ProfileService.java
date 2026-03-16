package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.ProfileDTO;
import com.revconnect.RevConnectWeb.entity.Profiles;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.ProfileRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository){
        this.profileRepository=profileRepository;
        this.userRepository=userRepository;
    }

    public ProfileDTO createProfile(Long userId, ProfileDTO dto){
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profiles profile=new Profiles(
                user,
                dto.getName(),
                dto.getBio(),
                dto.getProfilePicUrl(),
                dto.getLocation(),
                dto.getWebsiteUrl(),
                dto.getPrivacy()
        );
        Profiles saved=profileRepository.save(profile);

        return new ProfileDTO(
                saved.getBio(),
                saved.getName(),
                saved.getUserId(),
                saved.getProfilePicUrl(),
                saved.getLocation(),
                saved.getWebsiteUrl(),
                saved.getPrivacy()
        );

    }

    public ProfileDTO updateProfile(Long userId, ProfileDTO dto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profiles profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // update fields (only update if value is provided)
        if (dto.getName() != null) profile.setName(dto.getName());
        if (dto.getBio() != null) profile.setBio(dto.getBio());
        if (dto.getProfilePicUrl() != null) profile.setProfilePicUrl(dto.getProfilePicUrl());
        if (dto.getLocation() != null) profile.setLocation(dto.getLocation());
        if (dto.getWebsiteUrl() != null) profile.setWebsiteUrl(dto.getWebsiteUrl());
        if (dto.getPrivacy() != null) profile.setPrivacy(dto.getPrivacy());

        profile.setUser(user); // keeps @MapsId relationship safe

        Profiles saved = profileRepository.save(profile);

        return new ProfileDTO(
                saved.getBio(),
                saved.getName(),
                saved.getUserId(),
                saved.getProfilePicUrl(),
                saved.getLocation(),
                saved.getWebsiteUrl(),
                saved.getPrivacy()
        );

    }


    public ProfileDTO getProfile(Long userId){
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profiles profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return new ProfileDTO(
                profile.getBio(),
                profile.getName(),
                profile.getUserId(),
                profile.getProfilePicUrl(),
                profile.getLocation(),
                profile.getWebsiteUrl(),
                profile.getPrivacy()
        );
    }

    public List<ProfileDTO> searchUserProfile(String name){
        List<Profiles> profile=profileRepository.findByNameContainingIgnoreCase(name);
        System.out.println("Hello");
        return profile.stream()
                .map(p -> new ProfileDTO(
                        p.getBio(),
                        p.getName(),
                        p.getUserId(),
                        p.getProfilePicUrl(),
                        p.getLocation(),
                        p.getWebsiteUrl(),
                        p.getPrivacy()
                ))
                .toList();

    }

}
