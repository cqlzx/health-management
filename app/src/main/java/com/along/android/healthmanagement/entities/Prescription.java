package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Prescription extends SugarRecord{
    private Long id;
    private String patientName, doctorName, disease, medication, startDate, endDate, intakeTimes;
    private int frequency;
    private boolean notificationEnabled;

    public Prescription() {

    }

    public Long getId(){
        return id;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getIntakeTimes() {
        return intakeTimes;
    }

    public void setIntakeTimes(String intakeTimes) {
        this.intakeTimes = intakeTimes;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isNotificationEnabled() {
        return notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
