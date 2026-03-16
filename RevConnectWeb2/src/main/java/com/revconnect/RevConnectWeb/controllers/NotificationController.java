package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.ProfileDTO;
import com.revconnect.RevConnectWeb.entity.Notification;
import com.revconnect.RevConnectWeb.entity.NotificationType;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import com.revconnect.RevConnectWeb.services.NotificationService;
import com.revconnect.RevConnectWeb.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService  notificationService;

    private final ProfileService userService;


    public NotificationController(NotificationService notificationService, ProfileService userService) {
        this.notificationService = notificationService;
        this.userService = userService;

    }

    // Send a notification (admin or system use case)
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestParam Long userId,
            @RequestParam String message,
            @RequestParam String type,
            @RequestParam(required = false) Long relatedEntityId) {

        // Example: using the type from a NotificationType enum
        NotificationType notificationType = NotificationType.valueOf(type.toUpperCase());
        // Get the user by ID (this should be part of your authentication system)
        // Assuming UserService has a method to get user by ID
        ProfileDTO profileDTO = userService.getProfile(userId);
        User user = convertToUser(profileDTO);
        notificationService.sendNotification(user, notificationType, message, relatedEntityId);
        return ResponseEntity.ok("Notification sent");
    }

    // Get unread notification count
    @GetMapping("/unread/count/{userId}")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        ProfileDTO profileDTO = userService.getProfile(userId);
        User user = convertToUser(profileDTO);        long unreadCount = notificationService.getUnreadCount(user);
        return ResponseEntity.ok(unreadCount);
    }

    // Get all notifications for a user
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Notification>> getNotificationHistory(@PathVariable Long userId) {
        ProfileDTO profileDTO = userService.getProfile(userId);
        User user = convertToUser(profileDTO);        List<Notification> notifications = notificationService.getNotificationHistory(user);
        return ResponseEntity.ok(notifications);
    }

    // Mark notification as read
    @PostMapping("/markAsRead/{notificationId}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
    public User convertToUser(ProfileDTO profileDTO) {
        User user = new User();
        user.setUserId(profileDTO.getUserId());
        user.setUsername(profileDTO.getName());
        return user;
    }

    // Mark all notifications as read
    @PostMapping("/markAllAsRead/{userId}")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId) {
//        User user = userService.getProfile(userId);
        ProfileDTO profileDTO = userService.getProfile(userId);
        User user = convertToUser(profileDTO);  // Convert
        notificationService.markAllAsRead(user);
        return ResponseEntity.ok().build();
    }
}
