package com.revconnect.RevConnectWeb.DTO;

import java.time.LocalDateTime;

public class PinnedPostDTO {

    private Long id;
    private Long userId;
    private Long postId;
    private String postContent;
    private Integer pinOrder;
    private LocalDateTime pinnedAt;

    public PinnedPostDTO() {}

    public PinnedPostDTO(Long id, Long userId, Long postId, String postContent,
                         Integer pinOrder, LocalDateTime pinnedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.postContent = postContent;
        this.pinOrder = pinOrder;
        this.pinnedAt = pinnedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getPostContent() { return postContent; }
    public void setPostContent(String postContent) { this.postContent = postContent; }

    public Integer getPinOrder() { return pinOrder; }
    public void setPinOrder(Integer pinOrder) { this.pinOrder = pinOrder; }

    public LocalDateTime getPinnedAt() { return pinnedAt; }
    public void setPinnedAt(LocalDateTime pinnedAt) { this.pinnedAt = pinnedAt; }
}
