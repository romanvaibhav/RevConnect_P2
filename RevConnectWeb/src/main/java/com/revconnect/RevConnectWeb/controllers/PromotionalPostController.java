package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.PostAnalyticsDTO;
import com.revconnect.RevConnectWeb.entity.PromotionalPost;
import com.revconnect.RevConnectWeb.services.PromotionalPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PromotionalPostController {

    @Autowired
    private PromotionalPostService postService;

    // Create a new promotional post
    @PostMapping
    public ResponseEntity<PromotionalPost> createPost(
            @RequestParam Long businessProfileId,
            @RequestParam String content,
            @RequestParam String imageUrl,
            @RequestParam List<Long> productIds,
            @RequestParam String ctaType,  // "Learn More", "Shop Now", etc.
            @RequestParam String ctaUrl) {

        PromotionalPost post = postService.createPost(businessProfileId, content, imageUrl, productIds,ctaType,ctaUrl);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }


    //Fetching promotinal posts
    @GetMapping
    public ResponseEntity<List<PromotionalPost>> getAllPosts() {
        List<PromotionalPost> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Pin a post
    @PutMapping("/{id}/pin")
    public ResponseEntity<PromotionalPost> pinPost(@PathVariable Long id) {
        PromotionalPost post = postService.pinPost(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Unpin a post
    @PutMapping("/{id}/unpin")
    public ResponseEntity<PromotionalPost> unpinPost(@PathVariable Long id) {
        PromotionalPost post = postService.unpinPost(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //Promotional Post Analytics
//    @GetMapping("/{postId}/analytics")
//    public ResponseEntity<PostAnalyticsDTO> getPostAnalytics(@PathVariable Long postId) {
//        PostAnalyticsDTO analytics = postService.getPostAnalytics(postId);
//        return new ResponseEntity<>(analytics, HttpStatus.OK);
//    }
}
