package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.PostHashtags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostHashtagsRepository extends JpaRepository<PostHashtags, Long> {
    List<PostHashtags> findByPostPostId(Long postId);
    void deleteByPostPostId(Long postId);
    boolean existsByPostPostIdAndHashtagHashtagId(Long postId, Long hashtagId);
}
