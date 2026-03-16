package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.entity.*;
import com.revconnect.RevConnectWeb.repository.*;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikesRepository postLikeRepository;
    private final NotificationService notificationService;

    public PostLikeService(PostRepository postRepository,
                           UserRepository userRepository,
                           PostLikesRepository postLikeRepository,
                           NotificationService notificationService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
        this.notificationService = notificationService;
    }

    public String likePost(Long postId, Long userId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User liker = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (postLikeRepository.findByPostPostIdAndUserUserId(postId, userId).isPresent()) {
            return "Post already liked";
        }

        postLikeRepository.save(new PostLikes(post, liker));

        // Notify post owner (skip if user likes their own post)
        User postOwner = post.getUser();
        if (!postOwner.getUserId().equals(userId)) {
            notificationService.sendNotification(
                    postOwner,
                    NotificationType.LIKE,
                    liker.getUsername() + " liked your post",
                    postId
            );
        }

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
