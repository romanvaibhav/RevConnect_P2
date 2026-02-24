package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PromotionalPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private boolean isPinned;

    private String ctaType;  // "Learn More", "Shop Now", etc.
    private String ctaUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_profile_id")
    private BusinessProfile businessProfile;

    // A list to hold products tagged in the post
    @ManyToMany
    @JoinTable(
            name = "post_product_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> taggedProducts;

    // Default constructor
    public PromotionalPost() {}

    // Constructor
    public PromotionalPost(String content, String imageUrl, LocalDateTime createdAt, boolean isPinned,
                           BusinessProfile businessProfile, List<Product> taggedProducts, String ctaType, String ctaUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.isPinned = isPinned;
        this.businessProfile = businessProfile;
        this.taggedProducts = taggedProducts;
        this.ctaType = ctaType;
        this.ctaUrl = ctaUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        this.businessProfile = businessProfile;
    }

    public List<Product> getTaggedProducts() {
        return taggedProducts;
    }

    public void setTaggedProducts(List<Product> taggedProducts) {
        this.taggedProducts = taggedProducts;
    }

    public String getCtaType() {
        return ctaType;
    }

    public void setCtaType(String ctaType) {
        this.ctaType = ctaType;
    }

    public String getCtaUrl() {
        return ctaUrl;
    }

    public void setCtaUrl(String ctaUrl) {
        this.ctaUrl = ctaUrl;
    }
}