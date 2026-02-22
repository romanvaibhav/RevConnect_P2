package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.ProfileDTO;
import com.revconnect.RevConnectWeb.entity.Profiles;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.ProfileRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
