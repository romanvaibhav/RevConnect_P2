package com.revconnect.RevConnectWeb.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Posts post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private  User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_comment_id")
    private Comments parentComment;

    @Column(nullable = false)
    private String content;

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    public Comments(){}

    public Comments(Posts post, User user, Comments parentComment, String content) {
        this.post = post;
        this.user = user;
        this.parentComment = parentComment;
        this.content = content;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();
    }
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comments getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comments parentComment) {
        this.parentComment = parentComment;
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
}
