package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.ConnectionRequestDTO;
import com.revconnect.RevConnectWeb.DTO.ConnectionsDTO;
import com.revconnect.RevConnectWeb.services.ConnectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connections")
public class ConnectionsController {

    private ConnectionService connectionService;

    public ConnectionsController(ConnectionService connectionService){
        this.connectionService=connectionService;
    }

    //Send connection request
    @PostMapping("/request/{senderId}/{receiverId}")
    public ResponseEntity<ConnectionRequestDTO> sendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return ResponseEntity.ok(connectionService.sendRequest(senderId, receiverId));
    }


    //accept request
    @PostMapping("/request/{requestId}/accept/{userId}")
    public ResponseEntity<ConnectionRequestDTO> acceptRequest(@PathVariable Long requestId, @PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.acceptRequest(requestId, userId));
    }

    //reject request
    @PostMapping("/request/{requestId}/reject/{userId}")
    public ResponseEntity<ConnectionRequestDTO> rejectRequest(@PathVariable Long requestId, @PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.rejectRequest(requestId, userId));
    }

    //view pending requests
    @GetMapping("/requests/pending/{userId}")
    public ResponseEntity<List<ConnectionRequestDTO>> viewPending(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.viewPendingRequests(userId));
    }

    //all connections
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<ConnectionsDTO>> viewConnections(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.viewConnections(userId));
    }

    //Remove a connection
    @DeleteMapping("/{userId}/{connectionId}")
    public ResponseEntity<String> removeConnection(@PathVariable Long userId, @PathVariable Long connectionId) {
        connectionService.removeConnection(userId, connectionId);
        return ResponseEntity.ok("Connection removed successfully");
    }


}
