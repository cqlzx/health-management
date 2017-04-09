package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;

public class Meal extends SugarRecord{
    private Long id, uid;

    //Timestamp of the date
    private Long date;

    //foodIds format: "1,3,4"
    private String type, foodIds;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(String foodIds) {
        this.foodIds = foodIds;
    }

    public Long getMealCalories() {
        //TODO: get calories of the meal
        return 0L;
    }
}
