package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.ShareRequestDTO;
import com.revconnect.RevConnectWeb.DTO.ShareResponseDTO;
import com.revconnect.RevConnectWeb.entity.Posts;
import com.revconnect.RevConnectWeb.entity.Share;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.PostRepository;
import com.revconnect.RevConnectWeb.repository.ShareRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareService {

    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ShareService(ShareRepository shareRepository,
                        PostRepository postRepository,
                        UserRepository userRepository) {
        this.shareRepository = shareRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // CREATE SHARE
    public ShareResponseDTO createShare(ShareRequestDTO dto) {

        Posts originalPost = postRepository.findById(dto.getOriginalPostId())
                .orElseThrow(() -> new RuntimeException("Original post not found"));

        User sharedBy = userRepository.findById(dto.getSharedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ FIXED METHOD NAME HERE
        if (shareRepository.existsByOriginalPostPostIdAndSharedByUserId(
                dto.getOriginalPostId(),
                dto.getSharedByUserId()
        )) {
            throw new RuntimeException("You already shared this post");
        }

        Share share = new Share(originalPost, sharedBy, dto.getCommentaryText());
        Share saved = shareRepository.save(share);

        return mapToResponse(saved);
    }

    // GET SHARES BY USER
    public List<ShareResponseDTO> getSharesByUser(Long userId) {
        // ✅ FIXED METHOD NAME HERE
        return shareRepository.findBySharedByUserIdOrderBySharedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET SHARES BY ORIGINAL POST
    public List<ShareResponseDTO> getSharesByOriginalPost(Long postId) {
        return shareRepository.findByOriginalPostPostIdOrderBySharedAtDesc(postId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET ALL SHARES
    public List<ShareResponseDTO> getAllShares() {
        return shareRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ShareResponseDTO mapToResponse(Share share) {
        return new ShareResponseDTO(
                share.getShareId(),
                share.getOriginalPost().getPostId(),
                share.getOriginalPost().getContent(),
                share.getSharedBy().getUserId(),
                share.getSharedBy().getUsername(),
                share.getCommentaryText(),
                share.getSharedAt()
        );
    }
}