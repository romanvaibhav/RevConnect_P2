package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
    Optional<PostLikes> findByPostPostIdAndUserUserId(Long postId, Long userId);

    long countByPostPostId(Long postId);

}
