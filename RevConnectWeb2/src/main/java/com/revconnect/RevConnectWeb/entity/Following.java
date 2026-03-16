package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id","following_id"})
)
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="follower_id",nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="following_id",nullable = false)
    private User following;

    @Column(name="followed_at", updatable = false)
    private LocalDateTime followedAt;

    public Following(){}

    public Following(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
    @PrePersist
    protected void onFollow() {
        this.followedAt = LocalDateTime.now();
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    public LocalDateTime getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(LocalDateTime followedAt) {
        this.followedAt = followedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
