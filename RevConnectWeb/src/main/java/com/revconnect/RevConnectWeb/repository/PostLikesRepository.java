package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
    Optional<PostLikes> findByPostPostIdAndUserUserId(Long postId, Long userId);

    long countByPostPostId(Long postId);

}
