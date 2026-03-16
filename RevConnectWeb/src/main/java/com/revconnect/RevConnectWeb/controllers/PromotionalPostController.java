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

    @PostMapping
    public ResponseEntity<PromotionalPostDTO> createPost(
            @RequestParam Long businessProfileId,
            @RequestParam String content,
            @RequestParam(required = false, defaultValue = "") String imageUrl,
            @RequestParam(required = false) List<Long> productIds,
            @RequestParam String ctaType,
            @RequestParam String ctaUrl) {

        if (productIds == null) {
            productIds = List.of();
        }

        PromotionalPostDTO postDTO = postService.createPost(
                businessProfileId,
                content,
                imageUrl,
                ctaType,
                ctaUrl
        );

        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PromotionalPost>> getAllPosts() {
        List<PromotionalPost> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/{id}/pin")
    public ResponseEntity<PromotionalPost> pinPost(@PathVariable Long id) {
        PromotionalPost post = postService.pinPost(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{id}/unpin")
    public ResponseEntity<PromotionalPost> unpinPost(@PathVariable Long id) {
        PromotionalPost post = postService.unpinPost(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}