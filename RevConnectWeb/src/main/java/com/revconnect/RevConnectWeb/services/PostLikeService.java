package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.entity.PostLikes;
import com.revconnect.RevConnectWeb.entity.Posts;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.PostLikesRepository;
import com.revconnect.RevConnectWeb.repository.PostRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikesRepository postLikeRepository;

    public PostLikeService(PostRepository postRepository,
                           UserRepository userRepository,
                           PostLikesRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository=postLikeRepository;
    }

    public String likePost(Long postId, Long userId) {

        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (postLikeRepository
                .findByPostPostIdAndUserUserId(postId, userId)
                .isPresent()) {
            return "Post already liked";
        }

        PostLikes like = new PostLikes(post, user);
        postLikeRepository.save(like);
        return "Post liked successfully";
    }

    public String unlikePost(Long postId, Long userId) {
        PostLikes like = postLikeRepository
                .findByPostPostIdAndUserUserId(postId, userId)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        postLikeRepository.delete(like);
        return "Post unliked successfully";
    }



}
