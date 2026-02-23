package com.revconnect.RevConnectWeb.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private List<String> hashtags;

    public PostDTO() {}


//    List<String> hashtags
    public PostDTO(Long postId,
                   Long userId,
                   String content,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt
                   ) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
//        this.hashtags = hashtags;
    }

//    public List<String> getHashtags() { return hashtags; }
//    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}