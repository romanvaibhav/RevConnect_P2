package com.revconnect.RevConnectWeb.controller;

import com.revconnect.RevConnectWeb.DTO.FollowingDTO;

import com.revconnect.RevConnectWeb.services.FollowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowingController {

    private final FollowingService followingService;

    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    //Follow a user
    @PostMapping("/follow/{followerId}/{followingId}")
    public ResponseEntity<FollowingDTO> follow(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        return ResponseEntity.ok(followingService.follow(followerId, followingId));
    }

    //Unfollow a user
    @DeleteMapping("/unfollow/{followerId}/{followingId}")
    public ResponseEntity<Void> unfollow(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        followingService.unfollow(followerId, followingId);
        return ResponseEntity.noContent().build();
    }

    //Get following list
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<FollowingDTO>> getFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(followingService.getFollowingList(userId));
    }

    //Get followers list
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FollowingDTO>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followingService.getFollowersList(userId));
    }
}