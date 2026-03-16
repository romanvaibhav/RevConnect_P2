package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.BusinessHoursDTO;
import com.revconnect.RevConnectWeb.entity.BusinessHours;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.BusinessHoursRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessHoursService {

    private final BusinessHoursRepository businessHoursRepository;
    private final UserRepository userRepository;

    public BusinessHoursService(BusinessHoursRepository businessHoursRepository,
                                 UserRepository userRepository) {
        this.businessHoursRepository = businessHoursRepository;
        this.userRepository = userRepository;
    }

    /** Set or update business hours for a specific day (0=Sun, 1=Mon, ... 6=Sat) */
    @Transactional
    public BusinessHoursDTO setBusinessHours(Long userId, BusinessHoursDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BusinessHours hours = businessHoursRepository
                .findByBusinessUserUserIdAndDaysOfWeek(userId, dto.getDayOfWeek())
                .orElse(new BusinessHours());

        hours.setBusinessUser(user);
        hours.setDaysOfWeek(dto.getDayOfWeek());
        hours.setOpenTime(dto.getOpenTime());
        hours.setCloseTime(dto.getCloseTime());
        hours.setClosed(dto.getIsClosed() != null && dto.getIsClosed());

        BusinessHours saved = businessHoursRepository.save(hours);
        return mapToDTO(saved);
    }

    /** Get all business hours for a user */
    public List<BusinessHoursDTO> getBusinessHours(Long userId) {
        return businessHoursRepository.findByBusinessUserUserIdOrderByDaysOfWeekAsc(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /** Update a specific business hours entry by its ID */
    @Transactional
    public BusinessHoursDTO updateBusinessHours(Long hoursId, BusinessHoursDTO dto) {
        BusinessHours hours = businessHoursRepository.findById(hoursId)
                .orElseThrow(() -> new RuntimeException("Business hours entry not found with id: " + hoursId));

        if (dto.getOpenTime() != null) hours.setOpenTime(dto.getOpenTime());
        if (dto.getCloseTime() != null) hours.setCloseTime(dto.getCloseTime());
        if (dto.getIsClosed() != null) hours.setClosed(dto.getIsClosed());

        return mapToDTO(businessHoursRepository.save(hours));
    }

    /** Delete a specific business hours entry */
    @Transactional
    public void deleteBusinessHours(Long hoursId) {
        BusinessHours hours = businessHoursRepository.findById(hoursId)
                .orElseThrow(() -> new RuntimeException("Business hours entry not found with id: " + hoursId));
        businessHoursRepository.delete(hours);
    }

    /** Delete all business hours for a user */
    @Transactional
    public void deleteAllBusinessHours(Long userId) {
        businessHoursRepository.deleteByBusinessUserUserId(userId);
    }

    // ---------- helper ----------
    private BusinessHoursDTO mapToDTO(BusinessHours h) {
        BusinessHoursDTO dto = new BusinessHoursDTO();
        dto.setBusinessProfileId(h.getBusinessUser().getUserId());
        dto.setDayOfWeek(h.getDaysOfWeek());
        dto.setOpenTime(h.getOpenTime());
        dto.setCloseTime(h.getCloseTime());
        dto.setIsClosed(h.getClosed());
        return dto;
    }
}
