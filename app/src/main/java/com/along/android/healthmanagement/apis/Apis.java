package com.along.android.healthmanagement.apis;

public class Apis {
    public static final String PROTOCOL = "http://";
    public static final String HOST = "mysite.local";
    public static final String PORT = "";

    public static String getUrl() {
        return PROTOCOL + HOST + PORT;
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

    //food
    public static final String addFood() {
        return getUrl() + "/food/addFood";
    }

    public static final String delFood() {
        return getUrl() + "/food/delFood";
    }

    // meal
    public static final String addMeal() {
        return getUrl() + "/meal/addMeal";
    }

    public static final String editMeal() {
        return getUrl() + "/meal/editMeal";
    }

    public static final String getMeal() {
        return getUrl() + "/meal/getMeal";
    }

    // water
    public static final String addWater() {
        return getUrl() + "/waterconsumption/addWater";
    }

    public static final String editWater() {
        return getUrl() + "/waterconsumption/editWater";
    }

    // diet
    public static final String addDiet() {
        return getUrl() + "/diet/addDiet";
    }

    public static final String getDiet() {
        return getUrl() + "/diet/getDiet";
    }

    // notes
    public static final String insertNote() {
        return getUrl() + "/note/insert";
    }

    public static final String delNote() {
        return getUrl() + "/note/delNote";
    }

    public static final String getNotes() {
        return getUrl() + "/note/getNotes";
    }

    public static final String getById() {
        return getUrl() + "/note/getById";
    }

    public static final String upload() {
        return getUrl() + "/upload/do_upload";
    }
}
