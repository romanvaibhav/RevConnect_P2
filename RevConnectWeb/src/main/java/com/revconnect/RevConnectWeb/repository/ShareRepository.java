package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long> {

    // Get shares done by a user
    List<Share> findBySharedByUserIdOrderBySharedAtDesc(Long userId);

    // Get all shares of a specific original post
    List<Share> findByOriginalPostPostIdOrderBySharedAtDesc(Long postId);

    // Check duplicate share by same user for same post
    boolean existsByOriginalPostPostIdAndSharedByUserId(Long postId, Long userId);
}