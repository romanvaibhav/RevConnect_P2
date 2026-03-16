package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.HashTagsDTO;
import com.revconnect.RevConnectWeb.services.HashTagsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hashtags")
public class HashTagsController {

    private final HashTagsService hashTagsService;

    public HashTagsController(HashTagsService hashTagsService) {
        this.hashTagsService = hashTagsService;
    }

    /** Add a hashtag to a post */
    @PostMapping("/add")
    public ResponseEntity<HashTagsDTO> addHashTag(@RequestBody HashTagsDTO dto) {
        return ResponseEntity.ok(hashTagsService.addHashTag(dto));
    }

    /** Update a hashtag */
    @PutMapping("/{hashtagId}")
    public ResponseEntity<HashTagsDTO> updateHashTag(@PathVariable Long hashtagId,
                                                      @RequestBody HashTagsDTO dto) {
        return ResponseEntity.ok(hashTagsService.updateHashTag(hashtagId, dto));
    }

    /** Delete a hashtag */
    @DeleteMapping("/{hashtagId}")
    public ResponseEntity<String> deleteHashTag(@PathVariable Long hashtagId) {
        return ResponseEntity.ok(hashTagsService.deleteHashTag(hashtagId));
    }

    /** Search hashtags by keyword */
    @GetMapping("/search")
    public ResponseEntity<List<HashTagsDTO>> searchHashTags(@RequestParam String q) {
        return ResponseEntity.ok(hashTagsService.searchHashTags(q));
    }

    /** Get all hashtags for a specific post */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<HashTagsDTO>> getHashTagsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(hashTagsService.getHashTagsByPost(postId));
    }

    /** Get all posts using a specific hashtag */
    @GetMapping("/tag")
    public ResponseEntity<List<HashTagsDTO>> getPostsByHashTag(@RequestParam String tag) {
        return ResponseEntity.ok(hashTagsService.getPostsByHashTag(tag));
    }
}
