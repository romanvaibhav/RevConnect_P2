package com.revconnect.RevConnectWeb.repository;


import com.revconnect.RevConnectWeb.entity.HashTags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTagsRepository extends JpaRepository<HashTags,Long> {
    boolean existsByPostPostIdAndTagIgnoreCase(Long postId, String tag);

    List<HashTags> findByTagIgnoreCase(String tag);

    List<HashTags> findByPostPostId(Long postId);

    void deleteByPostPostIdAndTagIgnoreCase(Long postId, String tag);
}
