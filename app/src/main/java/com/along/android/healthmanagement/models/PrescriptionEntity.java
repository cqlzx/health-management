package com.along.android.healthmanagement.models;

/**
 * Created by fenghongyu on 18/3/14.
 */

public class PrescriptionEntity {
    private Long id;
    private String patientName, doctorName, disease, medication, rxcuis, startDate, endDate, intakeTimes, frequency;
    private boolean notificationEnabled;

    public PrescriptionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getRxcuis() {
        return rxcuis;
    }

    public void setRxcuis(String rxcuis) {
        this.rxcuis = rxcuis;
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

    public String getIntakeTimes() {
        return intakeTimes;
    }

    public void setIntakeTimes(String intakeTimes) {
        this.intakeTimes = intakeTimes;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isNotificationEnabled() {
        return notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }
}
