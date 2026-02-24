package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="business_profiles")
public class BusinessProfile {

    @Id
    @Column(name="user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String category;

    @Column(name="detailed_bio", length = 2000)
    private String detailedBio;

    @Column(name="contact_email")
    private String contactEmail;

    private String address;

    // One-to-many relationship with Product
    @OneToMany(mappedBy = "businessProfile", cascade = CascadeType.ALL)
    private List<Product> products;

    public BusinessProfile() {}

    // Getters and Setters for the fields

    public BusinessProfile(User user, String detailedBio, String category, String contactEmail, String address, List<Product> products) {
        this.user = user;
        this.detailedBio = detailedBio;
        this.category = category;
        this.contactEmail = contactEmail;
        this.address = address;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetailedBio() {
        return detailedBio;
    }

    public void setDetailedBio(String detailedBio) {
        this.detailedBio = detailedBio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
// Other getters and setters...
}