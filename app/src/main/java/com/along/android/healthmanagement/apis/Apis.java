package com.along.android.healthmanagement.apis;

/**
 * Created by fenghongyu on 18/3/13.
 */

public class Apis {
    public static final String PROTOCOL = "http://";
    public static final String HOST = "10.108.120.248";
    public static final String PORT = "3000";

    public static String getUrl() {
        return PROTOCOL + HOST + ":" + PORT;
    }

    // register or login
    public static String getRegisterUrl () {
        return getUrl() + "/users/register";
    }

    public static String getLoginUrl () {
        return getUrl() + "/users/login";
    }

    public static String getUserUrl () {
        return getUrl() + "/users/getUser";
    }

    // prescription
    public static String postPrescriptionUrl() {
        return getUrl() + "/prescription/prescription";
    }

    public static final String getPrescriptionUrl() {
        return getUrl() + "/prescription/getPrescription";
    }

    // medicine
    public static final String postMedicineUrl() {
        return getUrl() + "/medicine/medicine";
    }

    public static final String getMedicineUrl() {
        return getUrl() + "/medicine/getMedicine";
    }

    public static final String updateMedicineUrl() {
        return getUrl() + "/medicine/updateMed";
    }

    public static final String getCurrentMedication() {
        return getUrl() + "/prescription/currentMedication";
    }

    public static final String getMedicationHistory() {
        return getUrl() + "/prescription/medicationHistory";
    }

    public static final String getDelMedication() {
        return getUrl() + "/prescription/delMedication";
    }

    //contact

    public static final String getContact() {
        return getUrl() + "/contact/get";
    }

    public static final String insertContact() {
        return getUrl() + "/contact/insert";
    }
}
