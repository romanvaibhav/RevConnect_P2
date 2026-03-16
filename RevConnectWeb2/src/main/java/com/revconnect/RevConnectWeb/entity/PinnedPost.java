package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="pinned_post",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","post_id"})
)
public class PinnedPost {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private Posts post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name="pinned_at", updatable = false)
    private LocalDateTime pinnedAt;

    @Column(name="pin_order")
    private Integer pinOrder;

    public PinnedPost(){}

    public PinnedPost(User user, Posts post, Integer pinOrder) {
        this.user = user;
        this.post = post;
        this.pinOrder = pinOrder;
    }

    @PrePersist
    protected void onPin() {
        this.pinnedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public LocalDateTime getPinnedAt() {
        return pinnedAt;
    }

    public void setPinnedAt(LocalDateTime pinnedAt) {
        this.pinnedAt = pinnedAt;
    }

    public Integer getPinOrder() {
        return pinOrder;
    }

    public void setPinOrder(Integer pinOrder) {
        this.pinOrder = pinOrder;
    }
}
