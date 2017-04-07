package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;

public class Diet extends SugarRecord{
    private Long id, uid, waterConsumption, date;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    //Value is meal id
    private Long breakfast, lunch, dinner;

    public Long getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Long breakfast) {
        this.breakfast = breakfast;
    }

    public Long getLunch() {
        return lunch;
    }

    public void setLunch(Long lunch) {
        this.lunch = lunch;
    }

    public Long getDinner() {
        return dinner;
    }

    public void setDinner(Long dinner) {
        this.dinner = dinner;
    }

    public Long getWaterConsumption() {
        return waterConsumption;
    }

    public void setWaterConsumption(Long waterConsumption) {
        this.waterConsumption = waterConsumption;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDateCalories() {
        //TODO: get calories of the day
        return 0L;
    }
}
