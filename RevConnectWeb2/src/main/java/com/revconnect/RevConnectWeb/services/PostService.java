package com.revconnect.RevConnectWeb.services;


import com.revconnect.RevConnectWeb.DTO.PostDTO;
import com.revconnect.RevConnectWeb.entity.Posts;
import com.revconnect.RevConnectWeb.entity.Profiles;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.*;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostLikesRepository postLikeRepository;

    private final CommentsRepository commentRepository;
    private final ProfileRepository profileRepository;


    public PostService(PostRepository postRepository,UserRepository userRepository,PostLikesRepository postLikeRepository,CommentsRepository commentRepository,ProfileRepository profileRepository){
        this.postRepository=postRepository;
        this.userRepository=userRepository;
        this.commentRepository=commentRepository;
        this.postLikeRepository=postLikeRepository;
        this.profileRepository=profileRepository;
    }

    public PostDTO createPost(Long userId, PostDTO postDTO){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Posts post = new Posts(user, postDTO.getContent());

        Posts savedPost = postRepository.save(post);
        return new PostDTO(
                savedPost.getPostId(),
                savedPost.getUser().getUserId(),
                savedPost.getContent(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt(),
                postLikeRepository.countByPostPostId(post.getPostId()),
                commentRepository.countByPostPostId(post.getPostId()),
                ""

        );
    }

    public String deletePost(Long postId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postRepository.delete(post);

        return "Post Deleted Successfully";
    }


    public List<PostDTO> getAllPost(){

        List<Posts> posts = postRepository.findAll(
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return posts.stream()
                .map(post -> {
                    Profiles profile = profileRepository.findByUserUserId(post.getUser().getUserId());
                    String name = (profile != null) ? profile.getName() : "Unknown";

                    return new PostDTO(
                            post.getPostId(),
                            post.getUser().getUserId(),
                            post.getContent(),
                            post.getCreatedAt(),
                            post.getUpdatedAt(),
                            postLikeRepository.countByPostPostId(post.getPostId()),
                            commentRepository.countByPostPostId(post.getPostId()),
                            name // ✅ String
                    );
                })
                .toList();
    }

    public List<PostDTO> getUserPosts(Long userId){
        List<Posts> posts=postRepository.findByUserUserIdOrderByCreatedAtDesc(userId);
        return posts.stream()
                .map(post -> {
                    Profiles profile = profileRepository.findByUserUserId(post.getUser().getUserId());
                    String name = (profile != null) ? profile.getName() : "Unknown";

                    return new PostDTO(
                            post.getPostId(),
                            post.getUser().getUserId(),
                            post.getContent(),
                            post.getCreatedAt(),
                            post.getUpdatedAt(),
                            postLikeRepository.countByPostPostId(post.getPostId()),
                            commentRepository.countByPostPostId(post.getPostId()),
                            name // ✅ String
                    );
                })
                .toList();
    }

    public PostDTO updatePost(Long postId,PostDTO postDTO){
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setContent(postDTO.getContent());

        Posts updatedPost = postRepository.save(post);

        return new PostDTO(
                updatedPost.getPostId(),
                updatedPost.getUser().getUserId(),
                updatedPost.getContent(),
                updatedPost.getCreatedAt(),
                updatedPost.getUpdatedAt(),
                postLikeRepository.countByPostPostId(post.getPostId()),
                commentRepository.countByPostPostId(post.getPostId()),
                ""


        );

    }
}

