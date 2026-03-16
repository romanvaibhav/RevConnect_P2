package com.revconnect.RevConnectWeb.DTO;

import java.time.LocalDateTime;

public class ConnectionsDTO {

    private Long id;
    private Long user1Id;
    private String user1Name;
    private Long user2Id;
    private String user2Name;
    private LocalDateTime connectedAt;

    public ConnectionsDTO() {}

    public ConnectionsDTO(Long id, Long user1Id, String user1Name,
                          Long user2Id, String user2Name,
                          LocalDateTime connectedAt) {
        this.id = id;
        this.user1Id = user1Id;
        this.user1Name = user1Name;
        this.user2Id = user2Id;
        this.user2Name = user2Name;
        this.connectedAt = connectedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Long user1Id) {
        this.user1Id = user1Id;
    }

    public String getUser1Name() {
        return user1Name;
    }

    public void setUser1Name(String user1Name) {
        this.user1Name = user1Name;
    }

    public Long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Long user2Id) {
        this.user2Id = user2Id;
    }

    public String getUser2Name() {
        return user2Name;
    }

    public void setUser2Name(String user2Name) {
        this.user2Name = user2Name;
    }

    public LocalDateTime getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(LocalDateTime connectedAt) {
        this.connectedAt = connectedAt;
    }
}
