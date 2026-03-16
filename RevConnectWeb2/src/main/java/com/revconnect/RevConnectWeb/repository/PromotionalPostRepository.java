package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.PromotionalPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionalPostRepository extends JpaRepository<PromotionalPost, Long> {
    List<PromotionalPost> findByUserUserIdOrderByCreatedAtDesc(Long userId);
    List<PromotionalPost> findByIsPinnedTrueOrderByCreatedAtDesc();
}
