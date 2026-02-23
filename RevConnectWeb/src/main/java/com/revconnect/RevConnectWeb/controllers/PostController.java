package com.revconnect.RevConnectWeb.controllers;


import com.revconnect.RevConnectWeb.DTO.PostDTO;
import com.revconnect.RevConnectWeb.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService=postService;
    }

    // Create Post
    @PostMapping("/create/{userId}")
    public PostDTO createPost(@PathVariable Long userId, @RequestBody PostDTO postDTO){
        System.out.println("CONTENT = " + postDTO.getContent());
        return postService.createPost(userId,postDTO);
    }

    @PatchMapping("/update/{postId}")
    public PostDTO updatePost(@PathVariable Long postId,@RequestBody PostDTO postDTO){
        return postService.updatePost(postId,postDTO);

    }

    //Delete Posts
    @DeleteMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }

    //Get all Posts
    @GetMapping("/getposts")
    public List<PostDTO> getAllPosts(){
        return postService.getAllPost();
    }

    //Get Posts of user
    @GetMapping("/userposts/{userId}")
    public List<PostDTO> getUserPosts(@PathVariable Long userId){
        return postService.getUserPosts(userId);
    }



}
