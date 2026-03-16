package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.PostAnalyticsDTO;
import com.revconnect.RevConnectWeb.repository.CommentsRepository;
import com.revconnect.RevConnectWeb.repository.PostLikesRepository;
import com.revconnect.RevConnectWeb.repository.PostRepository;
import com.revconnect.RevConnectWeb.repository.ShareRepository;
import org.springframework.stereotype.Service;

@Service
public class PostAnalyticsService {

    private final PostLikesRepository postLikesRepository;
    private final CommentsRepository commentsRepository;
    private final ShareRepository shareRepository;
    private final PostRepository postRepository;

    public PostAnalyticsService(PostLikesRepository postLikesRepository,
                                 CommentsRepository commentsRepository,
                                 ShareRepository shareRepository,
                                 PostRepository postRepository) {
        this.postLikesRepository = postLikesRepository;
        this.commentsRepository = commentsRepository;
        this.shareRepository = shareRepository;
        this.postRepository = postRepository;
    }

    /**
     * Get analytics for a specific post.
     * uniqueViewerCount is approximated by summing likes + comments + shares (distinct engagement).
     */
    public PostAnalyticsDTO getPostAnalytics(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }

        long likeCount = postLikesRepository.countByPostPostId(postId);
        long commentCount = commentsRepository.countByPostPostId(postId);
        long shareCount = shareRepository.countByOriginalPostPostId(postId);

        // Approximate unique engagement count (likes + comments + shares combined)
        long uniqueViewerCount = likeCount + commentCount + shareCount;

        return new PostAnalyticsDTO(likeCount, commentCount, shareCount, uniqueViewerCount);
    }
}
