package com.revconnect.RevConnectWeb.DTO;

import java.time.LocalTime;

public class BusinessHoursDTO {

    private Long businessProfileId;
    private Integer dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isClosed;


    public Long getBusinessProfileId() {
        return businessProfileId;
    }

    public void setBusinessProfileId(Long businessProfileId) {
        this.businessProfileId = businessProfileId;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }
}