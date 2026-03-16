package com.revconnect.RevConnectWeb.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="connection_requests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id","receiver_id"})
)
public class ConnectionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="request_id")
    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id",nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id",nullable = false)
    private  User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConnectionRequestStatus status;

    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name="responded_at")
    private LocalDateTime respondedAt;

    public ConnectionRequest(){}

    public ConnectionRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = ConnectionRequestStatus.PENDING;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public ConnectionRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionRequestStatus status) {
        this.status = status;
        if (status != ConnectionRequestStatus.PENDING) {
            this.respondedAt = LocalDateTime.now();
        }
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }
}
