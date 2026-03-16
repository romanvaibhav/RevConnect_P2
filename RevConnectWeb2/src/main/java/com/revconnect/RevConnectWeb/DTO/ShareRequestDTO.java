package com.revconnect.RevConnectWeb.DTO;

public class ShareRequestDTO {

    private Long originalPostId;
    private Long sharedByUserId;
    private String commentaryText;

    public ShareRequestDTO() {}

    public ShareRequestDTO(Long originalPostId, Long sharedByUserId, String commentaryText) {
        this.originalPostId = originalPostId;
        this.sharedByUserId = sharedByUserId;
        this.commentaryText = commentaryText;
    }

    public Long getOriginalPostId() {
        return originalPostId;
    }

    public void setOriginalPostId(Long originalPostId) {
        this.originalPostId = originalPostId;
    }

    public Long getSharedByUserId() {
        return sharedByUserId;
    }

    public void setSharedByUserId(Long sharedByUserId) {
        this.sharedByUserId = sharedByUserId;
    }

    public String getCommentaryText() {
        return commentaryText;
    }

    public void setCommentaryText(String commentaryText) {
        this.commentaryText = commentaryText;
    }
}