package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.ConnectionRequestDTO;
import com.revconnect.RevConnectWeb.DTO.ConnectionsDTO;
import com.revconnect.RevConnectWeb.entity.*;
import com.revconnect.RevConnectWeb.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ConnectionService {

    private final ConnectionRequestRepository requestRepo;
    private final ConnectionsRepository connectionsRepo;
    private final UserRepository userRepo;
    private final NotificationService notificationService;

    public ConnectionService(ConnectionRequestRepository requestRepo,
                             ConnectionsRepository connectionsRepo,
                             UserRepository userRepo,
                             NotificationService notificationService) {
        this.requestRepo = requestRepo;
        this.connectionsRepo = connectionsRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
    }

    public ConnectionRequestDTO sendRequest(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) throw new RuntimeException("Cannot send request to yourself");

        User sender = userRepo.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepo.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (requestRepo.existsBySenderAndReceiver(sender, receiver))
            throw new RuntimeException("Connection request already sent");

        if (connectionsRepo.existsByUser1AndUser2(sender, receiver) ||
                connectionsRepo.existsByUser1AndUser2(receiver, sender))
            throw new RuntimeException("Already connected");

        ConnectionRequest request = new ConnectionRequest(sender, receiver);
        requestRepo.save(request);

        // Notify receiver
        notificationService.sendNotification(
                receiver,
                NotificationType.CONNECTION_REQUEST,
                sender.getUsername() + " sent you a connection request",
                request.getRequestId()
        );

        return mapToDTO(request);
    }

    public ConnectionRequestDTO acceptRequest(Long requestId, Long userId) {
        ConnectionRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getReceiver().getUserId().equals(userId))
            throw new RuntimeException("Only the receiver can accept the request");

        request.setStatus(ConnectionRequestStatus.ACCEPTED);
        connectionsRepo.save(new Connections(request.getSender(), request.getReceiver()));

        // Notify sender that their request was accepted
        notificationService.sendNotification(
                request.getSender(),
                NotificationType.CONNECTION_ACCEPTED,
                request.getReceiver().getUsername() + " accepted your connection request",
                requestId
        );

        return mapToDTO(request);
    }

    public ConnectionRequestDTO rejectRequest(Long requestId, Long userId) {
        ConnectionRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getReceiver().getUserId().equals(userId))
            throw new RuntimeException("Only the receiver can reject the request");

        request.setStatus(ConnectionRequestStatus.REJECTED);
        return mapToDTO(request);
    }

    public List<ConnectionRequestDTO> viewPendingRequests(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return requestRepo.findBySenderOrReceiverAndStatus(user, user, ConnectionRequestStatus.PENDING)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ConnectionsDTO> viewConnections(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return connectionsRepo.findByUser1OrUser2(user, user)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void removeConnection(Long userId, Long connectionId) {
        Connections connection = connectionsRepo.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        if (!connection.getUser1().getUserId().equals(userId) &&
                !connection.getUser2().getUserId().equals(userId))
            throw new RuntimeException("You can only remove your own connections");

        connectionsRepo.delete(connection);
    }

    private ConnectionRequestDTO mapToDTO(ConnectionRequest r) {
        return new ConnectionRequestDTO(
                r.getRequestId(), r.getSender().getUserId(), r.getSender().getUsername(),
                r.getReceiver().getUserId(), r.getReceiver().getUsername(),
                r.getStatus(), r.getCreatedAt(), r.getRespondedAt());
    }

    private ConnectionsDTO mapToDTO(Connections c) {
        return new ConnectionsDTO(
                c.getId(), c.getUser1().getUserId(), c.getUser1().getUsername(),
                c.getUser2().getUserId(), c.getUser2().getUsername(), c.getConnectedAt());
    }
}
