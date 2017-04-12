package com.along.android.healthmanagement.entities;


import com.orm.SugarRecord;

public class WaterConsumption extends SugarRecord{
    private Long id, uid, date;
    private double number;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
