package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.ExternalLinksDTO;
import com.revconnect.RevConnectWeb.entity.ExternalLinks;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.ExternalLinksRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalLinksService {

    @Autowired
    private ExternalLinksRepository externalLinksRepository;

    @Autowired
    private UserRepository userRepository;

    // Add a new external link
    public void addExternalLink(ExternalLinksDTO externalLinksDTO) {
        // Find the user by userId
        User user = userRepository.findById(externalLinksDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create new ExternalLinks object and map from DTO
        ExternalLinks externalLink = new ExternalLinks();
        externalLink.setUser(user);
        externalLink.setLabel(externalLinksDTO.getLabel());
        externalLink.setUrl(externalLinksDTO.getUrl());
        externalLink.setLinkType(externalLinksDTO.getLinkType());

        // Save the external link
        externalLinksRepository.save(externalLink);
    }

    public void updateExternalLink(Long id, ExternalLinksDTO externalLinksDTO) {
        // Find the existing external link by ID
        ExternalLinks existingLink = externalLinksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("External link not found"));

        // Update the fields from DTO
        existingLink.setLabel(externalLinksDTO.getLabel());
        existingLink.setUrl(externalLinksDTO.getUrl());
        existingLink.setLinkType(externalLinksDTO.getLinkType());

        // Save the updated external link
        externalLinksRepository.save(existingLink);
    }

    public void deleteExternalLink(Long id) {
        // Find the existing external link by ID
        ExternalLinks existingLink = externalLinksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("External link not found"));

        // Delete the external link
        externalLinksRepository.delete(existingLink);
    }

    public List<ExternalLinks> getExternalLinksByUserId(Long userId) {
        return externalLinksRepository.findByUser_UserId(userId);
    }
}
