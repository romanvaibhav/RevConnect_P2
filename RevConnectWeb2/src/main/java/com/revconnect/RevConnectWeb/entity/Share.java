package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="shares")
public class Share {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="share_id")
    private Long shareId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="original_post_id",nullable = false)
    private Posts originalPost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="shared_by_id",nullable = false)
    private User sharedBy;

    @Column(name="shared_at",nullable = false)
    private LocalDateTime sharedAt;

    @Lob
    @Column(name="commentry_text")
    private String commentaryText;

    public Share(){}
    @PrePersist
    protected void onShare() {
        this.sharedAt = LocalDateTime.now();
    }

    public Share(Posts originalPost, User sharedBy, String commentaryText) {
        this.originalPost = originalPost;
        this.sharedBy = sharedBy;
        this.commentaryText = commentaryText;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Posts getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(Posts originalPost) {
        this.originalPost = originalPost;
    }

    public User getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(User sharedBy) {
        this.sharedBy = sharedBy;
    }

    public LocalDateTime getSharedAt() {
        return sharedAt;
    }

    public void setSharedAt(LocalDateTime sharedAt) {
        this.sharedAt = sharedAt;
    }

    public String getCommentaryText() {
        return commentaryText;
    }

    public void setCommentaryText(String commentaryText) {
        this.commentaryText = commentaryText;
    }
}
