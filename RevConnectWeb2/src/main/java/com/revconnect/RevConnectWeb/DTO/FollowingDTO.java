package com.revconnect.RevConnectWeb.DTO;

import java.time.LocalDateTime;

public class FollowingDTO {

    private Long id;
    private Long followerId;
    private Long followingId;
    private LocalDateTime followedAt;

    public FollowingDTO() {}
    public FollowingDTO(Long id, Long followerId, Long followingId, LocalDateTime followedAt) {
        this.id = id;
        this.followerId = followerId;
        this.followingId = followingId;
        this.followedAt = followedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    public LocalDateTime getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(LocalDateTime followedAt) {
        this.followedAt = followedAt;
    }
}