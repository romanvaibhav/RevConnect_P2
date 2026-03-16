package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.ConnectionRequest;
import com.revconnect.RevConnectWeb.entity.ConnectionRequestStatus;
import com.revconnect.RevConnectWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest,Long> {
    boolean existsBySenderAndReceiver(User sender, User receiver);
    List<ConnectionRequest> findBySenderOrReceiverAndStatus(User sender, User receiver, ConnectionRequestStatus status);
}
