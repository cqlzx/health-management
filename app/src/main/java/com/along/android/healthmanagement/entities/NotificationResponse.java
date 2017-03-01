package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class NotificationResponse extends SugarRecord{
    private Long id, prescriptionId, userId, time;
    private Boolean notificationResponse;

    public NotificationResponse() {

    }

    public Long getId(){
        return id;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getNotificationResponse() {
        return notificationResponse;
    }

    public void setNotificationResponse(Boolean notificationResponse) {
        this.notificationResponse = notificationResponse;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
