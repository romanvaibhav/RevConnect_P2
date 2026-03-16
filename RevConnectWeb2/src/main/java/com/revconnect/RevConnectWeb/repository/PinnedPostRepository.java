package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.PinnedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PinnedPostRepository extends JpaRepository<PinnedPost, Long> {
    List<PinnedPost> findByUserUserIdOrderByPinOrderAsc(Long userId);
    Optional<PinnedPost> findByUserUserIdAndPostPostId(Long userId, Long postId);
    boolean existsByUserUserIdAndPostPostId(Long userId, Long postId);
    long countByUserUserId(Long userId);
}
