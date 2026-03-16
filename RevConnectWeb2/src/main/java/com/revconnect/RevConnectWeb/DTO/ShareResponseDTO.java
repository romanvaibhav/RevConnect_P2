package com.revconnect.RevConnectWeb.DTO;

import java.time.LocalDateTime;

public class ShareResponseDTO {

    private Long shareId;

    private Long originalPostId;
    private String originalPostContent;

    private Long sharedByUserId;
    private String sharedByUsername;

    private String commentaryText;
    private LocalDateTime sharedAt;

    public ShareResponseDTO() {}

    public ShareResponseDTO(Long shareId,
                            Long originalPostId,
                            String originalPostContent,
                            Long sharedByUserId,
                            String sharedByUsername,
                            String commentaryText,
                            LocalDateTime sharedAt) {
        this.shareId = shareId;
        this.originalPostId = originalPostId;
        this.originalPostContent = originalPostContent;
        this.sharedByUserId = sharedByUserId;
        this.sharedByUsername = sharedByUsername;
        this.commentaryText = commentaryText;
        this.sharedAt = sharedAt;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Long getOriginalPostId() {
        return originalPostId;
    }

    public void setOriginalPostId(Long originalPostId) {
        this.originalPostId = originalPostId;
    }

    public String getOriginalPostContent() {
        return originalPostContent;
    }

    public void setOriginalPostContent(String originalPostContent) {
        this.originalPostContent = originalPostContent;
    }

    public Long getSharedByUserId() {
        return sharedByUserId;
    }

    public void setSharedByUserId(Long sharedByUserId) {
        this.sharedByUserId = sharedByUserId;
    }

    public String getSharedByUsername() {
        return sharedByUsername;
    }

    public void setSharedByUsername(String sharedByUsername) {
        this.sharedByUsername = sharedByUsername;
    }

    public String getCommentaryText() {
        return commentaryText;
    }

    public void setCommentaryText(String commentaryText) {
        this.commentaryText = commentaryText;
    }

    public LocalDateTime getSharedAt() {
        return sharedAt;
    }

    public void setSharedAt(LocalDateTime sharedAt) {
        this.sharedAt = sharedAt;
    }
}