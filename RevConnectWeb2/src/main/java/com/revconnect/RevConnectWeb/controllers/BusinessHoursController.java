package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.BusinessHoursDTO;
import com.revconnect.RevConnectWeb.services.BusinessHoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businessHours")
public class BusinessHoursController {

    private final BusinessHoursService businessHoursService;

    public BusinessHoursController(BusinessHoursService businessHoursService) {
        this.businessHoursService = businessHoursService;
    }

    /** Set or upsert hours for a day (0=Sun … 6=Sat) */
    @PostMapping("/set/{userId}")
    public ResponseEntity<BusinessHoursDTO> setHours(@PathVariable Long userId,
                                                      @RequestBody BusinessHoursDTO dto) {
        return ResponseEntity.ok(businessHoursService.setBusinessHours(userId, dto));
    }

    /** Get all business hours for a user */
    @GetMapping("/{userId}")
    public ResponseEntity<List<BusinessHoursDTO>> getHours(@PathVariable Long userId) {
        return ResponseEntity.ok(businessHoursService.getBusinessHours(userId));
    }

    /** Update a specific hours entry by its ID */
    @PutMapping("/{hoursId}")
    public ResponseEntity<BusinessHoursDTO> updateHours(@PathVariable Long hoursId,
                                                         @RequestBody BusinessHoursDTO dto) {
        return ResponseEntity.ok(businessHoursService.updateBusinessHours(hoursId, dto));
    }

    /** Delete a specific hours entry */
    @DeleteMapping("/{hoursId}")
    public ResponseEntity<String> deleteHours(@PathVariable Long hoursId) {
        businessHoursService.deleteBusinessHours(hoursId);
        return ResponseEntity.ok("Business hours entry deleted successfully");
    }

    /** Delete all business hours for a user */
    @DeleteMapping("/all/{userId}")
    public ResponseEntity<String> deleteAllHours(@PathVariable Long userId) {
        businessHoursService.deleteAllBusinessHours(userId);
        return ResponseEntity.ok("All business hours deleted for user " + userId);
    }
}
