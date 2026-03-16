package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Long> {
    List<Comments> findByPost_PostIdOrderByCreatedAtAsc(Long postId);
    Long countByPostPostId(Long postId);

}
