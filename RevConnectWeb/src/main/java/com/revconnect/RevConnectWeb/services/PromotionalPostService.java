package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.PromotionalPostDTO;
import com.revconnect.RevConnectWeb.entity.BusinessProfile;
import com.revconnect.RevConnectWeb.entity.PromotionalPost;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.BusinessProfileRepository;
import com.revconnect.RevConnectWeb.repository.PostLikesRepository;
import com.revconnect.RevConnectWeb.repository.ProductRepository;
import com.revconnect.RevConnectWeb.repository.PromotionalPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromotionalPostService {

    @Autowired
    private PromotionalPostRepository postRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;


    //Create Post
    public PromotionalPostDTO createPost(Long businessProfileId, String content, String imageUrl, String ctaType, String ctaUrl) {
        BusinessProfile businessProfile = businessProfileRepository.findById(businessProfileId)
                .orElseThrow(() -> new RuntimeException("BusinessProfile not found"));

        User user = businessProfile.getUser(); // make sure BusinessProfile has getUser()

        PromotionalPost post = new PromotionalPost(
                user,
                content,
                imageUrl,
                LocalDateTime.now(),
                false,
                ctaType,
                ctaUrl
        );

        PromotionalPost savedPost = postRepository.save(post);

        return mapToDTO(savedPost);
    }

    //pin
    public PromotionalPost pinPost(Long postId) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setPinned(true);
        return postRepository.save(post);
    }

    //all posts
    public List<PromotionalPost> getAllPosts() {
        return postRepository.findAll();
    }


    //unpin
    public PromotionalPost unpinPost(Long postId) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setPinned(false);
        return postRepository.save(post);
    }


    private PromotionalPostDTO mapToDTO(PromotionalPost post) {
        return new PromotionalPostDTO(
                post.getId(),
                post.getContent(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.isPinned(),
                post.getCtaType(),
                post.getCtaUrl(),
                post.getUser() != null ? post.getUser().getUsername() : null
        );
    }
}