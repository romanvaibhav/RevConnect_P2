package com.revconnect.RevConnectWeb.DTO;

import com.revconnect.RevConnectWeb.entity.ConnectionRequestStatus;
import java.time.LocalDateTime;

public class ConnectionRequestDTO {

    private Long requestId;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private ConnectionRequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;

    public ConnectionRequestDTO() {}

    public ConnectionRequestDTO(Long requestId,
                                Long senderId,
                                String senderName,
                                Long receiverId,
                                String receiverName,
                                ConnectionRequestStatus status,
                                LocalDateTime createdAt,
                                LocalDateTime respondedAt) {
        this.requestId = requestId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.status = status;
        this.createdAt = createdAt;
        this.respondedAt = respondedAt;
    }

    // Getters and Setters
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public ConnectionRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionRequestStatus status) {
        this.status = status;
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