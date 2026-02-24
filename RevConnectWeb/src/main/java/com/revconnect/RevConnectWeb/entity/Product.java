package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Correct the join column to refer to 'user_id' in BusinessProfile
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_profile_id", referencedColumnName = "user_id", nullable = false)
    private BusinessProfile businessProfile;

    private String name;
    private String description;
    private Double price;
    private String category;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    // Default constructor
    public Product() {}

    // Constructor
    public Product(String name, BusinessProfile businessProfile, String description, Double price, String category, String imageUrl) {
        this.name = name;
        this.businessProfile = businessProfile;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        this.businessProfile = businessProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}