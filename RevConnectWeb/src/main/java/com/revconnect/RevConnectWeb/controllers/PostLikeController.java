package com.revconnect.RevConnectWeb.controllers;


import com.revconnect.RevConnectWeb.services.PostLikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }


    //Post like
    @PostMapping("/{postId}/like/{userId}")
    public String likePost(@PathVariable Long postId,
                           @PathVariable Long userId) {
        return postLikeService.likePost(postId, userId);
    }

    //Unliking the post
    @DeleteMapping("/{postId}/unlike/{userId}")
    public String unlikePost(@PathVariable Long postId,
                             @PathVariable Long userId) {
        return postLikeService.unlikePost(postId, userId);
    }

}
