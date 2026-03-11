package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.PostAnalyticsDTO;
import com.revconnect.RevConnectWeb.entity.BusinessProfile;
import com.revconnect.RevConnectWeb.entity.Product;
import com.revconnect.RevConnectWeb.entity.PromotionalPost;
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

    // Create a new Promotional Post
    public PromotionalPost createPost(Long businessProfileId, String content, String imageUrl, List<Long> productIds,String ctaType,String ctaUrl) {
        BusinessProfile businessProfile = businessProfileRepository.findById(businessProfileId)
                .orElseThrow(() -> new RuntimeException("BusinessProfile not found"));

        List<Product> taggedProducts = productRepository.findAllById(productIds);
        PromotionalPost post = new PromotionalPost(
                content,                // content
                imageUrl,               // imageUrl
                LocalDateTime.now(),    // createdAt
                false,                  // pinned status
                businessProfile,        // businessProfile
                taggedProducts,        // taggedProducts
                ctaType,
                ctaUrl
        );

        return postRepository.save(post);
    }

    // Pin a post
    public PromotionalPost pinPost(Long postId) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setPinned(true);
        return postRepository.save(post);
    }
    // Retrieve all PromotionalPosts
    public List<PromotionalPost> getAllPosts() {
        // Return all posts from the database
        return postRepository.findAll();
    }

    // Unpin a post
    public PromotionalPost unpinPost(Long postId) {
        PromotionalPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setPinned(false);
        return postRepository.save(post);
    }

//    public PostAnalyticsDTO getPostAnalytics(Long postId) {
//        Long likeCount = postLikeRepository.countByPostId(postId);
//        Long commentCount = postCommentRepository.countByPostId(postId);
//        Long shareCount = postShareRepository.countByPostId(postId);
//        Long uniqueViewerCount = postViewRepository.countDistinctByPostId(postId);
//
//        return new PostAnalyticsDTO(likeCount, commentCount, shareCount, uniqueViewerCount);
//    }
}