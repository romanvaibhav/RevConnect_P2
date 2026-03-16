package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.PinnedPostDTO;
import com.revconnect.RevConnectWeb.entity.PinnedPost;
import com.revconnect.RevConnectWeb.entity.Posts;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.PinnedPostRepository;
import com.revconnect.RevConnectWeb.repository.PostRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PinnedPostService {

    private static final int MAX_PINNED_POSTS = 5;

    private final PinnedPostRepository pinnedPostRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PinnedPostService(PinnedPostRepository pinnedPostRepository,
                              PostRepository postRepository,
                              UserRepository userRepository) {
        this.pinnedPostRepository = pinnedPostRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /** Pin a post for a user */
    @Transactional
    public PinnedPostDTO pinPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (pinnedPostRepository.existsByUserUserIdAndPostPostId(userId, postId)) {
            throw new RuntimeException("Post is already pinned");
        }

        long currentCount = pinnedPostRepository.countByUserUserId(userId);
        if (currentCount >= MAX_PINNED_POSTS) {
            throw new RuntimeException("Cannot pin more than " + MAX_PINNED_POSTS + " posts");
        }

        int pinOrder = (int) currentCount + 1;
        PinnedPost pinnedPost = new PinnedPost(user, post, pinOrder);
        PinnedPost saved = pinnedPostRepository.save(pinnedPost);
        return mapToDTO(saved);
    }

    /** Unpin a post for a user */
    @Transactional
    public void unpinPost(Long userId, Long postId) {
        PinnedPost pinnedPost = pinnedPostRepository.findByUserUserIdAndPostPostId(userId, postId)
                .orElseThrow(() -> new RuntimeException("Pinned post not found"));
        pinnedPostRepository.delete(pinnedPost);
    }

    /** Get all pinned posts for a user */
    public List<PinnedPostDTO> getPinnedPosts(Long userId) {
        return pinnedPostRepository.findByUserUserIdOrderByPinOrderAsc(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /** Update pin order */
    @Transactional
    public PinnedPostDTO updatePinOrder(Long pinnedPostId, Integer newOrder) {
        PinnedPost pinnedPost = pinnedPostRepository.findById(pinnedPostId)
                .orElseThrow(() -> new RuntimeException("Pinned post not found"));
        pinnedPost.setPinOrder(newOrder);
        return mapToDTO(pinnedPostRepository.save(pinnedPost));
    }

    private PinnedPostDTO mapToDTO(PinnedPost p) {
        return new PinnedPostDTO(
                p.getId(),
                p.getUser().getUserId(),
                p.getPost().getPostId(),
                p.getPost().getContent(),
                p.getPinOrder(),
                p.getPinnedAt()
        );
    }
}
