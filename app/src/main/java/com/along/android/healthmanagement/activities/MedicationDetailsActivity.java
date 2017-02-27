package com.along.android.healthmanagement.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.MedicationDetailAdapter;
import com.along.android.healthmanagement.entities.Medicine;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class MedicationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String selectedPrescriptionItemId = getIntent().getStringExtra("selectedPrescriptionItemId"); //selectedPrescriptionItemId

        TextView patientNameText = (TextView) findViewById(R.id.xlMDPatientName);
        TextView doctorNameText = (TextView) findViewById(R.id.xlMDDoctorName);
        TextView diseaseText = (TextView) findViewById(R.id.xlMDDisease);
        TextView prescriptionDateText = (TextView) findViewById(R.id.xlMDPrescriptionDate);

        // sharePreferences  UserId & Real Name
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long userIdS = sp.getLong("uid", 0);
        User user = EntityManager.findById(User.class, userIdS);
        if(user != null) {
            patientNameText.setText("Patient Name:"+ user.getRealname());
        }
        System.out.println("----->>>>>>>>>>>>");


        doctorNameText.setText("Doctor Name: Bertram"); // prescription.getDoctorName()

        diseaseText.setText("Disease: Have a cold"); // prescription.getDisease()

        prescriptionDateText.setText("Prescription Date: 2/20,2017");
        System.out.println(diseaseText.getText());

        //listview  prescription.getMedication()

        // Testing ----->
        // Create a list of medicine
        ArrayList<Medicine> medicines = new ArrayList<Medicine>();

        // Get the actual data from the database
        for(int i=1; i<=1;i++) {
            Medicine medicine = new Medicine();
            medicine.setName(i + ". Rabonik, Telsar, Telnol");
//            medicine.setStartDate("StartDate:1/2,2017");
//            medicine.setEndDate("EndDate:3/30,2017");

            medicines.add(medicine);
            medicine.save();
        }

        List<Medicine> medicineList = Medicine.listAll(Medicine.class);

        MedicationDetailAdapter medicationDetailAdapter =
                new MedicationDetailAdapter(this, medicineList);

        ListView listView = (ListView) findViewById(R.id.medication_detail_list);

        listView.setAdapter(medicationDetailAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
