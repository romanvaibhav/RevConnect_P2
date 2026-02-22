package com.revconnect.RevConnectWeb.entity;


import jakarta.persistence.*;

@Entity
@Table(name="profiles")
public class Profiles {

    @Id
    @Column(name="user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String bio;

    @Column(name="profile_pic_url")
    private String profilePicUrl;

    private String location;

    @Column(name="website_url")
    private String websiteUrl;

    private String privacy;

    public Profiles(){};

    public Profiles(User user, String name, String bio, String profilePicUrl, String location, String websiteUrl, String privacy) {
        this.user = user;
        this.name = name;
        this.bio = bio;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
