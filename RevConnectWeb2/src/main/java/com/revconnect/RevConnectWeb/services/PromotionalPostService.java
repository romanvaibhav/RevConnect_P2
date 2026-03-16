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
import java.util.stream.Collectors;

@Service
public class PromotionalPostService {

    @Autowired
    private PromotionalPostRepository postRepository;

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;

    /** Create a new promotional post */
    public PromotionalPostDTO createPost(Long businessProfileId, String content,
                                         String imageUrl, String ctaType, String ctaUrl) {
        BusinessProfile businessProfile = businessProfileRepository.findById(businessProfileId)
                .orElseThrow(() -> new RuntimeException("BusinessProfile not found"));

        User user = businessProfile.getUser();

        PromotionalPost post = new PromotionalPost(
                user, content, imageUrl, LocalDateTime.now(), false, ctaType, ctaUrl
        );

        return mapToDTO(postRepository.save(post));
    }

    /** Update an existing promotional post */
    public PromotionalPostDTO updatePost(Long postId, String content, String imageUrl,
                                          String ctaType, String ctaUrl) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Promotional post not found with id: " + postId));

        if (content != null && !content.isBlank()) post.setContent(content);
        if (imageUrl != null) post.setImageUrl(imageUrl);
        if (ctaType != null) post.setCtaType(ctaType);
        if (ctaUrl != null) post.setCtaUrl(ctaUrl);

        return mapToDTO(postRepository.save(post));
    }

    /** Delete a promotional post */
    public void deletePost(Long postId) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Promotional post not found with id: " + postId));
        postRepository.delete(post);
    }

    /** Get all promotional posts */
    public List<PromotionalPost> getAllPosts() {
        return postRepository.findAll();
    }

    /** Get all promotional posts by a specific business profile */
    public List<PromotionalPostDTO> getPostsByBusiness(Long businessProfileId) {
        BusinessProfile bp = businessProfileRepository.findById(businessProfileId)
                .orElseThrow(() -> new RuntimeException("BusinessProfile not found"));

        return postRepository.findByUserUserIdOrderByCreatedAtDesc(bp.getUser().getUserId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /** Get a single promotional post by ID */
    public PromotionalPostDTO getPostById(Long postId) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Promotional post not found with id: " + postId));
        return mapToDTO(post);
    }

    /** Pin a promotional post */
    public PromotionalPost pinPost(Long postId) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setPinned(true);
        return postRepository.save(post);
    }

    /** Unpin a promotional post */
    public PromotionalPost unpinPost(Long postId) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setPinned(false);
        return postRepository.save(post);
    }

    // ---------- helper ----------
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
