package com.along.android.healthmanagement.entities;

/**
 * Created by RitenVithlani on 3/26/17.
 */

public class ConflictingMedicine {
    Medicine medicine1;
    Medicine medicine2;
    String externalUrl;

    public Medicine getMedicine1() {
        return medicine1;
    }

    public void setMedicine1(Medicine medicine1) {
        this.medicine1 = medicine1;
    }

    public Medicine getMedicine2() {
        return medicine2;
    }

    public void setMedicine2(Medicine medicine2) {
        this.medicine2 = medicine2;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }
}
