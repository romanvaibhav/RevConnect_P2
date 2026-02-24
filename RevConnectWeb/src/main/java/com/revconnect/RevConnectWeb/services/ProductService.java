package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.entity.BusinessProfile;
import com.revconnect.RevConnectWeb.entity.Product;
import com.revconnect.RevConnectWeb.DTO.ProductDTO;
import com.revconnect.RevConnectWeb.repository.BusinessProfileRepository;
import com.revconnect.RevConnectWeb.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        // Fetch the BusinessProfile entity using businessProfileId
        BusinessProfile businessProfile = businessProfileRepository.findById(productDTO.getBusinessProfileId())
                .orElseThrow(() -> new RuntimeException("BusinessProfile not found for ID: " + productDTO.getBusinessProfileId()));

        Product product = productDTO.toEntity(businessProfileRepository); // Provide the repository here
        product.setBusinessProfile(businessProfile); // Set the business profile explicitly

        Product savedProduct = productRepository.save(product);

        return ProductDTO.fromEntity(savedProduct);
    }

    public ProductDTO getProductById(Long id) {
        // Fetch the product by its ID
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + id));

        // Return the product as a DTO
        return ProductDTO.fromEntity(product);
    }

    public List<ProductDTO> getAllProducts() {
        // Fetch all products from the repository
        List<Product> products = productRepository.findAll();

        // Convert the list of products to a list of ProductDTOs and return it
        return products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Update an existing product
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        // Fetch the product to be updated
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + id));

        // Update the product with values from the DTO
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setImageUrl(productDTO.getImageUrl());

        // Set the BusinessProfile again if the businessProfileId is updated
        if (productDTO.getBusinessProfileId() != null) {
            BusinessProfile businessProfile = businessProfileRepository.findById(productDTO.getBusinessProfileId())
                    .orElseThrow(() -> new RuntimeException("BusinessProfile not found for ID: " + productDTO.getBusinessProfileId()));
            existingProduct.setBusinessProfile(businessProfile);
        }

        // Save the updated product
        Product updatedProduct = productRepository.save(existingProduct);

        // Return the updated product as a DTO
        return ProductDTO.fromEntity(updatedProduct);
    }

    // Delete a product by ID
    @Transactional
    public void deleteProduct(Long id) {
        // Check if the product exists before deleting
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + id));

        // Delete the product
        productRepository.delete(existingProduct);
    }


}