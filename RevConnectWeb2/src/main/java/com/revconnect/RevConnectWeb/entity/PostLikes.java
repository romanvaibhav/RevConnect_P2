package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"post_id", "user_id"})
        })
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="liked_id")
    private Long likedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private Posts post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(name="liked_at",nullable = false)
    private LocalDateTime likedAt;

    public PostLikes(){}

    public PostLikes(Posts post, User user) {
        this.post = post;
        this.user = user;
    }

    @PrePersist
    protected void onLike() {
        this.likedAt = LocalDateTime.now();
    }
    public Long getLikedId() {
        return likedId;
    }

    public void setLikedId(Long likedId) {
        this.likedId = likedId;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
