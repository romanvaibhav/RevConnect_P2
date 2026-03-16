package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="connections",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id_1","user_id_2"}))
public class Connections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id_1",nullable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id_2",nullable = false)
    private User user2;

    @Column(name="connected_at",updatable = false)
    private LocalDateTime connectedAt;

    public Connections(){}

    @PrePersist
    protected void onConnect() {
        this.connectedAt = LocalDateTime.now();
    }

    public Connections(User user2, User user1) {
        this.user2 = user2;
        this.user1 = user1;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public LocalDateTime getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(LocalDateTime connectedAt) {
        this.connectedAt = connectedAt;
    }
}
