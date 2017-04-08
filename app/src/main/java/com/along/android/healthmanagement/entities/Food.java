package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;

public class Food extends SugarRecord{
    private Long id;
    private String name, foodId, unit;

    //calories only store one single serve food's calories
    private Long amount, calories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public Long getFoodCalories(){
        return getCalories() * getAmount();
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
