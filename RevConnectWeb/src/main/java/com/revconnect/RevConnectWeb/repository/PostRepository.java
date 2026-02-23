package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts,Long> {

    List<Posts> findByUserUserIdOrderByCreatedAtDesc(Long userId);}
