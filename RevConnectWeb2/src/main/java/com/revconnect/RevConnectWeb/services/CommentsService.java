package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.CommentsDTO;
import com.revconnect.RevConnectWeb.entity.*;
import com.revconnect.RevConnectWeb.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostRepository postsRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public CommentsService(CommentsRepository commentsRepository,
                           PostRepository postsRepository,
                           UserRepository userRepository,
                           NotificationService notificationService) {
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public CommentsDTO addComment(CommentsDTO dto) {
        Posts post = postsRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comments parentComment = null;
        if (dto.getParentCommentId() != null) {
            parentComment = commentsRepository.findById(dto.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }

        Comments comment = new Comments(post, user, parentComment, dto.getContent());
        Comments saved = commentsRepository.save(comment);

        // Notify post owner
        User postOwner = post.getUser();
        if (!postOwner.getUserId().equals(user.getUserId())) {
            notificationService.sendNotification(
                    postOwner,
                    NotificationType.COMMENT,
                    user.getUsername() + " commented on your post",
                    post.getPostId()
            );
        }

        return mapToDTO(saved);
    }

    public CommentsDTO updateComment(Long commentId, CommentsDTO dto) {
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getUser().getUserId().equals(dto.getUserId())) {
            throw new RuntimeException("You can only update your own comment");
        }
        comment.setContent(dto.getContent());
        return mapToDTO(commentsRepository.save(comment));
    }

    public void deleteComment(Long commentId, Long userId) {
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own comment");
        }
        commentsRepository.delete(comment);
    }

    public List<CommentsDTO> getCommentsByPost(Long postId) {
        return commentsRepository.findByPost_PostIdOrderByCreatedAtAsc(postId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private CommentsDTO mapToDTO(Comments comment) {
        return new CommentsDTO(
                comment.getCommentId(),
                comment.getPost().getPostId(),
                comment.getUser().getUserId(),
                comment.getUser().getUsername(),
                comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null,
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
