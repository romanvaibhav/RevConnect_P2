package com.revconnect.RevConnectWeb.DTO;



public class PostAnalyticsDTO {
    private Long likeCount;
    private Long commentCount;
    private Long shareCount;
    private Long uniqueViewerCount;

    public PostAnalyticsDTO(Long likeCount, Long commentCount, Long shareCount, Long uniqueViewerCount) {
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.uniqueViewerCount = uniqueViewerCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getUniqueViewerCount() {
        return uniqueViewerCount;
    }

    public void setUniqueViewerCount(Long uniqueViewerCount) {
        this.uniqueViewerCount = uniqueViewerCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    // Getters and setters
}
