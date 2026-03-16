package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Notification;
import com.revconnect.RevConnectWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserAndIsRead(User user, boolean isRead);

    List<Notification> findByUser(User user);

    long countByUserAndIsRead(User user, boolean isRead);  // Unrea
}
