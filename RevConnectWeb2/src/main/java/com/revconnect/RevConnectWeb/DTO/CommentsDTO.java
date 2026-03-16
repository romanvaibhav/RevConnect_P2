package com.revconnect.RevConnectWeb.DTO;


import java.time.LocalDateTime;

public class CommentsDTO {

    private Long commentId;
    private Long postId;
    private Long userId;
    private String username;        // optional but useful for display
    private Long parentCommentId;   // for replies
    private String content;
    private LocalDateTime createdAt;

    public CommentsDTO() {}

    public CommentsDTO(Long commentId,
                       Long postId,
                       Long userId,
                       String username,
                       Long parentCommentId,
                       String content,
                       LocalDateTime createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}