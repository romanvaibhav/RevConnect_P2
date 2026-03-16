package com.revconnect.RevConnectWeb.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionalPostDTO {

    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private boolean isPinned;
    private String ctaType;
    private String ctaUrl;
    private String profileName; // ← new field from BusinessProfile

    public PromotionalPostDTO() {}

    public PromotionalPostDTO(Long id, String content, String imageUrl, LocalDateTime createdAt,
                              boolean isPinned, String ctaType, String ctaUrl,
                              String profileName) {
        this.id = id;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.isPinned = isPinned;
        this.ctaType = ctaType;
        this.ctaUrl = ctaUrl;
        this.profileName = profileName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isPinned() { return isPinned; }
    public void setPinned(boolean pinned) { isPinned = pinned; }

    public String getCtaType() { return ctaType; }
    public void setCtaType(String ctaType) { this.ctaType = ctaType; }

    public String getCtaUrl() { return ctaUrl; }
    public void setCtaUrl(String ctaUrl) { this.ctaUrl = ctaUrl; }

    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }
}