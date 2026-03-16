package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
    List<BusinessHours> findByBusinessUserUserIdOrderByDaysOfWeekAsc(Long userId);
    Optional<BusinessHours> findByBusinessUserUserIdAndDaysOfWeek(Long userId, Integer dayOfWeek);
    boolean existsByBusinessUserUserIdAndDaysOfWeek(Long userId, Integer dayOfWeek);
    void deleteByBusinessUserUserId(Long userId);
}
