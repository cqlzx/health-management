package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Medicine extends SugarRecord{
    private Long id;
    private String name, timings;
    private int quantity, frequency;

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

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
