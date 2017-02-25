package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Medicine extends SugarRecord{
    private Long id;
    private String name, startDate, endDate, notificationTimes;
    private int dosage;

    public Medicine() {

    }

    public Long getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNotificationTimes() {
        return notificationTimes;
    }

    public void setNotificationTimes(String notificationTimes) {
        this.notificationTimes = notificationTimes;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

}
