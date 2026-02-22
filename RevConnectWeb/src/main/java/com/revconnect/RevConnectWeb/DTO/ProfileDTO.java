package com.revconnect.RevConnectWeb.DTO;

public class ProfileDTO {
    private Long userId;
    private String name;
    private String bio;
    private String profilePicUrl;
    private String location;
    private String websiteUrl;
    private String privacy;

    public ProfileDTO(){};

    public ProfileDTO(String bio, String name, Long userId, String profilePicUrl, String location, String websiteUrl, String privacy) {
        this.bio = bio;
        this.name = name;
        this.userId = userId;
        this.profilePicUrl = profilePicUrl;
        this.location = location;
        this.websiteUrl = websiteUrl;
        this.privacy = privacy;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
