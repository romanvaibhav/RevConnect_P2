package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.PostAnalyticsDTO;
import com.revconnect.RevConnectWeb.DTO.PostDTO;
import com.revconnect.RevConnectWeb.services.PostAnalyticsService;
import com.revconnect.RevConnectWeb.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    private final PostAnalyticsService postAnalyticsService;

    public PostController(PostService postService, PostAnalyticsService postAnalyticsService) {
        this.postService = postService;
        this.postAnalyticsService = postAnalyticsService;
    }

    // Create Post
    @PostMapping("/create/{userId}")
    public PostDTO createPost(@PathVariable Long userId, @RequestBody PostDTO postDTO) {
        System.out.println("CONTENT = " + postDTO.getContent());
        return postService.createPost(userId, postDTO);
    }

    // Update Post
    @PatchMapping("/update/{postId}")
    public PostDTO updatePost(@PathVariable Long postId, @RequestBody PostDTO postDTO) {
        return postService.updatePost(postId, postDTO);
    }

    // Delete Post
    @DeleteMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    // Get all Posts
    @GetMapping("/getposts")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPost();
    }

    // Get Posts of user
    @GetMapping("/userposts/{userId}")
    public List<PostDTO> getUserPosts(@PathVariable Long userId) {
        return postService.getUserPosts(userId);
    }

    // Get Post Analytics
    @GetMapping("/{postId}/analytics")
    public ResponseEntity<PostAnalyticsDTO> getPostAnalytics(@PathVariable Long postId) {
        return ResponseEntity.ok(postAnalyticsService.getPostAnalytics(postId));
    }
}
