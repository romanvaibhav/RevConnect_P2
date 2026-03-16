package com.revconnect.RevConnectWeb.entity;


import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class BusinessHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hours_id")
    private Long hoursId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="business_user_id",nullable = false)
    private User businessUser;

    @Column(name="day_of_week",nullable = false)
    private Integer daysOfWeek;


    @Column(name="open_time")
    private LocalTime openTime;

    @Column(name="close_time")
    private LocalTime closeTime;

    @Column(name="is_closed",nullable = false)
    private Boolean isClosed=false;

    public BusinessHours(){}

    public Long getHoursId() {
        return hoursId;
    }

    public void setHoursId(Long hoursId) {
        this.hoursId = hoursId;
    }

    public User getBusinessUser() {
        return businessUser;
    }

    public void setBusinessUser(User businessUser) {
        this.businessUser = businessUser;
    }

    public Integer getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Integer daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
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

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }
}
