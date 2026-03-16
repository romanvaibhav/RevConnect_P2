package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.HashTagsDTO;
import com.revconnect.RevConnectWeb.entity.HashTags;
import com.revconnect.RevConnectWeb.entity.Posts;
import com.revconnect.RevConnectWeb.repository.HashTagsRepository;
import com.revconnect.RevConnectWeb.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashTagsService {

    private final HashTagsRepository hashTagRepository;
    private final PostRepository postRepository;

    public HashTagsService(HashTagsRepository hashTagRepository, PostRepository postRepository) {
        this.hashTagRepository = hashTagRepository;
        this.postRepository = postRepository;
    }

    /** Add a hashtag to a post */
    @Transactional
    public HashTagsDTO addHashTag(HashTagsDTO dto) {
        Posts post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + dto.getPostId()));

        String normalized = normalizeTag(dto.getTag());

        if (hashTagRepository.existsByPostPostIdAndTagIgnoreCase(dto.getPostId(), normalized)) {
            throw new RuntimeException("Hashtag '" + normalized + "' already exists on this post");
        }

        HashTags hashTag = new HashTags(normalized, post);
        HashTags saved = hashTagRepository.save(hashTag);
        return mapToDTO(saved);
    }

    /** Update an existing hashtag */
    @Transactional
    public HashTagsDTO updateHashTag(Long hashtagId, HashTagsDTO dto) {
        HashTags existing = hashTagRepository.findById(hashtagId)
                .orElseThrow(() -> new RuntimeException("Hashtag not found with id: " + hashtagId));

        String normalized = normalizeTag(dto.getTag());
        existing.setTag(normalized);

        HashTags updated = hashTagRepository.save(existing);
        return mapToDTO(updated);
    }

    /** Delete a hashtag by id */
    @Transactional
    public String deleteHashTag(Long hashtagId) {
        HashTags existing = hashTagRepository.findById(hashtagId)
                .orElseThrow(() -> new RuntimeException("Hashtag not found with id: " + hashtagId));
        hashTagRepository.delete(existing);
        return "Hashtag deleted successfully";
    }

    /** Search hashtags by keyword (partial match) */
    public List<HashTagsDTO> searchHashTags(String keyword) {
        String normalized = normalizeTag(keyword);
        // Find all hashtags and filter by tag containing the keyword
        return hashTagRepository.findAll().stream()
                .filter(h -> h.getTag().toLowerCase().contains(normalized.toLowerCase()))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /** Get all hashtags for a specific post */
    public List<HashTagsDTO> getHashTagsByPost(Long postId) {
        return hashTagRepository.findByPostPostId(postId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /** Get all posts that use a specific hashtag */
    public List<HashTagsDTO> getPostsByHashTag(String tag) {
        String normalized = normalizeTag(tag);
        return hashTagRepository.findByTagIgnoreCase(normalized)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ---------- helpers ----------

    private String normalizeTag(String t) {
        if (t == null) return "";
        String tag = t.trim();
        if (tag.isEmpty()) return "";
        if (!tag.startsWith("#")) tag = "#" + tag;
        return tag.toLowerCase();
    }

    private HashTagsDTO mapToDTO(HashTags h) {
        return new HashTagsDTO(h.getHashtagId(), h.getTag(), h.getPost().getPostId());
    }
}
