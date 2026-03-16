package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts,Long> {

    List<Posts> findByUserUserIdOrderByCreatedAtDesc(Long userId);

}
