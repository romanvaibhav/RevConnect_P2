package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessProfileRepository extends JpaRepository<BusinessProfile,Long> {

    Optional<BusinessProfile> findByUserId(Long userId);
}
