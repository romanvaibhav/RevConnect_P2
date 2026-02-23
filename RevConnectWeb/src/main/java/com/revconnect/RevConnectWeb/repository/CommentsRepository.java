package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Long> {
    List<Comments> findByPost_PostIdOrderByCreatedAtAsc(Long postId);


}
