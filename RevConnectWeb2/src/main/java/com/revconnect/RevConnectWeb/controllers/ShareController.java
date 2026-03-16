package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.ShareRequestDTO;
import com.revconnect.RevConnectWeb.DTO.ShareResponseDTO;
import com.revconnect.RevConnectWeb.services.ShareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shares")
@CrossOrigin(origins = "http://localhost:4200")
public class ShareController {

    private final ShareService shareService;

    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    // CREATE SHARE
    @PostMapping
    public ResponseEntity<ShareResponseDTO> createShare(@RequestBody ShareRequestDTO dto) {
        return ResponseEntity.ok(shareService.createShare(dto));
    }

    // GET SHARES BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShareResponseDTO>> getSharesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(shareService.getSharesByUser(userId));
    }

    // GET SHARES OF A POST
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ShareResponseDTO>> getSharesByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(shareService.getSharesByOriginalPost(postId));
    }

    // GET ALL SHARES
    @GetMapping
    public ResponseEntity<List<ShareResponseDTO>> getAllShares() {
        return ResponseEntity.ok(shareService.getAllShares());
    }
}