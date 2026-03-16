package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.ShareRequestDTO;
import com.revconnect.RevConnectWeb.DTO.ShareResponseDTO;
import com.revconnect.RevConnectWeb.entity.*;
import com.revconnect.RevConnectWeb.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareService {

    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public ShareService(ShareRepository shareRepository,
                        PostRepository postRepository,
                        UserRepository userRepository,
                        NotificationService notificationService) {
        this.shareRepository = shareRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public ShareResponseDTO createShare(ShareRequestDTO dto) {
        Posts originalPost = postRepository.findById(dto.getOriginalPostId())
                .orElseThrow(() -> new RuntimeException("Original post not found"));
        User sharedBy = userRepository.findById(dto.getSharedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (shareRepository.existsByOriginalPostPostIdAndSharedByUserId(
                dto.getOriginalPostId(), dto.getSharedByUserId())) {
            throw new RuntimeException("You already shared this post");
        }

        Share share = new Share(originalPost, sharedBy, dto.getCommentaryText());
        Share saved = shareRepository.save(share);

        // Notify post owner
        User postOwner = originalPost.getUser();
        if (!postOwner.getUserId().equals(sharedBy.getUserId())) {
            notificationService.sendNotification(
                    postOwner,
                    NotificationType.SHARE,
                    sharedBy.getUsername() + " shared your post",
                    originalPost.getPostId()
            );
        }

        return mapToResponse(saved);
    }

    public List<ShareResponseDTO> getSharesByUser(Long userId) {
        return shareRepository.findBySharedByUserIdOrderBySharedAtDesc(userId)
                .stream().map(this::mapToResponse).toList();
    }

    public List<ShareResponseDTO> getSharesByOriginalPost(Long postId) {
        return shareRepository.findByOriginalPostPostIdOrderBySharedAtDesc(postId)
                .stream().map(this::mapToResponse).toList();
    }

    public List<ShareResponseDTO> getAllShares() {
        return shareRepository.findAll().stream().map(this::mapToResponse).toList();
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
