package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.entity.Notification;
import com.revconnect.RevConnectWeb.entity.NotificationType;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(User user, NotificationType type, String message, Long relatedEntityId) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(type);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRelatedEntityId(relatedEntityId);
        notificationRepository.save(notification);
    }


    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public long getUnreadCount(User user) {
        return notificationRepository.countByUserAndIsRead(user, false);
    }

    public List<Notification> getNotificationHistory(User user) {
        return notificationRepository.findByUser(user);
    }

    public void markAllAsRead(User user) {
        List<Notification> notifications = notificationRepository.findByUserAndIsRead(user, false);
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }
}
