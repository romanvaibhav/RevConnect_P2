package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.BusinessProfileDTO;
import com.revconnect.RevConnectWeb.entity.BusinessProfile;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.BusinessProfileRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessProfileService {

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private UserRepository userRepository;

    // Create or update the business profile
    public void createOrUpdateProfile(BusinessProfileDTO businessProfileDTO) {
        User user = userRepository.findById(businessProfileDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<BusinessProfile> existingProfile = businessProfileRepository.findByUserId(businessProfileDTO.getUserId());
        BusinessProfile businessProfile;

        if (existingProfile.isPresent()) {
            businessProfile = existingProfile.get();
        } else {
            businessProfile = new BusinessProfile();
        }

        businessProfile.setUser(user);
        businessProfile.setCategory(businessProfileDTO.getCategory());
        businessProfile.setDetailedBio(businessProfileDTO.getDetailedBio());
        businessProfile.setContactEmail(businessProfileDTO.getContactEmail());
        businessProfile.setAddress(businessProfileDTO.getAddress());

        // Save the profile (either update or create new)
        businessProfileRepository.save(businessProfile);
    }

    // Get business profile by userId
    public BusinessProfileDTO getProfileByUserId(Long userId) {
        BusinessProfile businessProfile = businessProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Business Profile not found for userId: " + userId));

        // Convert entity to DTO
        BusinessProfileDTO profileDTO = new BusinessProfileDTO();
        profileDTO.setUserId(businessProfile.getUserId());
        profileDTO.setCategory(businessProfile.getCategory());
        profileDTO.setDetailedBio(businessProfile.getDetailedBio());
        profileDTO.setContactEmail(businessProfile.getContactEmail());
        profileDTO.setAddress(businessProfile.getAddress());

        return profileDTO;
    }
}