package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Profiles;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profiles,Long> {

    List<Profiles> findByNameContainingIgnoreCase(String name);
    // ProfileRepository
    Profiles findByUserUserId(Long userId); }
