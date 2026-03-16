package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.PromotionalPostDTO;
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

    /** Create promotional post */
    @PostMapping
    public ResponseEntity<PromotionalPostDTO> createPost(
            @RequestParam Long businessProfileId,
            @RequestParam String content,
            @RequestParam(required = false, defaultValue = "") String imageUrl,
            @RequestParam String ctaType,
            @RequestParam String ctaUrl) {

        PromotionalPostDTO postDTO = postService.createPost(
                businessProfileId, content, imageUrl, ctaType, ctaUrl);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    /** Update promotional post */
    @PutMapping("/{id}")
    public ResponseEntity<PromotionalPostDTO> updatePost(
            @PathVariable Long id,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) String ctaType,
            @RequestParam(required = false) String ctaUrl) {

        return ResponseEntity.ok(postService.updatePost(id, content, imageUrl, ctaType, ctaUrl));
    }

    /** Delete promotional post */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Promotional post deleted successfully");
    }

    /** Get all promotional posts */
    @GetMapping
    public ResponseEntity<List<PromotionalPost>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    /** Get promotional post by ID */
    @GetMapping("/{id}")
    public ResponseEntity<PromotionalPostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    /** Get all promotional posts for a business profile */
    @GetMapping("/business/{businessProfileId}")
    public ResponseEntity<List<PromotionalPostDTO>> getPostsByBusiness(
            @PathVariable Long businessProfileId) {
        return ResponseEntity.ok(postService.getPostsByBusiness(businessProfileId));
    }

    /** Pin a promotional post */
    @PutMapping("/{id}/pin")
    public ResponseEntity<PromotionalPost> pinPost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.pinPost(id), HttpStatus.OK);
    }

    /** Unpin a promotional post */
    @PutMapping("/{id}/unpin")
    public ResponseEntity<PromotionalPost> unpinPost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.unpinPost(id), HttpStatus.OK);
    }
}
