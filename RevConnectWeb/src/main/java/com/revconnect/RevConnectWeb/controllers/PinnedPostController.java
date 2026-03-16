package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.PinnedPostDTO;
import com.revconnect.RevConnectWeb.services.PinnedPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/pinned")
public class PinnedPostController {

    private final PinnedPostService pinnedPostService;

    public PinnedPostController(PinnedPostService pinnedPostService) {
        this.pinnedPostService = pinnedPostService;
    }

    /** Pin a post */
    @PostMapping("/pin/{userId}/{postId}")
    public ResponseEntity<PinnedPostDTO> pinPost(@PathVariable Long userId,
                                                  @PathVariable Long postId) {
        return ResponseEntity.ok(pinnedPostService.pinPost(userId, postId));
    }

    /** Unpin a post */
    @DeleteMapping("/unpin/{userId}/{postId}")
    public ResponseEntity<String> unpinPost(@PathVariable Long userId,
                                             @PathVariable Long postId) {
        pinnedPostService.unpinPost(userId, postId);
        return ResponseEntity.ok("Post unpinned successfully");
    }

    /** Get all pinned posts for a user */
    @GetMapping("/{userId}")
    public ResponseEntity<List<PinnedPostDTO>> getPinnedPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(pinnedPostService.getPinnedPosts(userId));
    }

    /** Update pin order */
    @PatchMapping("/{pinnedPostId}/order")
    public ResponseEntity<PinnedPostDTO> updatePinOrder(@PathVariable Long pinnedPostId,
                                                         @RequestParam Integer order) {
        return ResponseEntity.ok(pinnedPostService.updatePinOrder(pinnedPostId, order));
    }
}
