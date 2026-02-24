package com.revconnect.RevConnectWeb.DTO;

import com.revconnect.RevConnectWeb.entity.Product;
import com.revconnect.RevConnectWeb.entity.BusinessProfile;
import com.revconnect.RevConnectWeb.repository.BusinessProfileRepository;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    private Long businessProfileId;

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, Double price, String category, String imageUrl, Long businessProfileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.businessProfileId = businessProfileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Long getBusinessProfileId() {
        return businessProfileId;
    }

    public void setBusinessProfileId(Long businessProfileId) {
        this.businessProfileId = businessProfileId;
    }

    // Convert a Product entity to a ProductDTO
    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getImageUrl(),
                product.getBusinessProfile() != null ? product.getBusinessProfile().getUserId() : null
        );
    }

    // Convert ProductDTO to Product entity, including fetching BusinessProfile based on ID
    public Product toEntity(BusinessProfileRepository businessProfileRepository) {
        // Fetch the BusinessProfile using businessProfileId
        BusinessProfile businessProfile = businessProfileRepository.findById(this.businessProfileId)
                .orElseThrow(() -> new RuntimeException("BusinessProfile not found for ID: " + this.businessProfileId));

        Product product = new Product();
        product.setBusinessProfile(businessProfile);
        product.setId(this.id);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setCategory(this.category);
        product.setImageUrl(this.imageUrl);

        return product;
    }
}