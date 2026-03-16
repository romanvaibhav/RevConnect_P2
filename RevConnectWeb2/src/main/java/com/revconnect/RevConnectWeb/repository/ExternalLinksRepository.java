package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.ExternalLinks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalLinksRepository extends JpaRepository<ExternalLinks,Long> {

    Optional<ExternalLinks> findById(Long id);
//    List<ExternalLinks> findByUser_Id(Long userId);
    List<ExternalLinks> findByUser_UserId(Long userId);
}
