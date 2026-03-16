package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.FollowingDTO;
import com.revconnect.RevConnectWeb.entity.*;
import com.revconnect.RevConnectWeb.repository.FollowingRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowingService {

    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public FollowingService(FollowingRepository followingRepository,
                            UserRepository userRepository,
                            NotificationService notificationService) {
        this.followingRepository = followingRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public FollowingDTO follow(Long followerId, Long followingId) {
        if (followerId.equals(followingId))
            throw new RuntimeException("You cannot follow yourself");

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if (!(following.getAccountType() == AccountType.BUSINESS))
            throw new RuntimeException("You can only follow business or creator accounts");

        if (followingRepository.findByFollowerAndFollowing(follower, following).isPresent())
            throw new RuntimeException("Already following this user");

        Following follow = new Following(follower, following);
        Following saved = followingRepository.save(follow);

        // Notify the followed user
        notificationService.sendNotification(
                following,
                NotificationType.NEW_FOLLOWER,
                follower.getUsername() + " started following you",
                follower.getUserId()
        );

        return new FollowingDTO(saved.getId(), saved.getFollower().getUserId(),
                saved.getFollowing().getUserId(), saved.getFollowedAt());
    }

    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        Following follow = followingRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("Follow record not found"));

        followingRepository.delete(follow);
    }

    public List<FollowingDTO> getFollowingList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return followingRepository.findAllByFollower(user).stream()
                .map(f -> new FollowingDTO(f.getId(), f.getFollower().getUserId(),
                        f.getFollowing().getUserId(), f.getFollowedAt()))
                .collect(Collectors.toList());
    }

    public List<FollowingDTO> getFollowersList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return followingRepository.findAllByFollowing(user).stream()
                .map(f -> new FollowingDTO(f.getId(), f.getFollower().getUserId(),
                        f.getFollowing().getUserId(), f.getFollowedAt()))
                .collect(Collectors.toList());
    }
}
