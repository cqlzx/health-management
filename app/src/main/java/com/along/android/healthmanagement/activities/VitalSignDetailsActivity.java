package com.along.android.healthmanagement.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.VitalSign;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.Calendar;
import java.util.List;

public class VitalSignDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_sign_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String selectedVitalSignItemId = getIntent().getStringExtra("selectedVitalSignItemId");
        // got the prescription
        VitalSign vitalSign = EntityManager.findById(VitalSign.class, Long.parseLong(selectedVitalSignItemId));


        TextView etWeight = (TextView) findViewById(R.id.tvWeight);
        TextView etHeight = (TextView) findViewById(R.id.tvHeight);
        TextView etSystolic = (TextView) findViewById(R.id.tvSystolic);
        TextView etDiastolic = (TextView) findViewById(R.id.tvDiastolic);
        TextView etBloodGlucose = (TextView) findViewById(R.id.tvBloodGlucose);
        TextView etHeartRate = (TextView) findViewById(R.id.tvHeartRate);
        TextView etBodyTemperature = (TextView) findViewById(R.id.tvBodyTemperature);


        String weightText = "weight: " + (null != vitalSign.getWeight() ? vitalSign.getWeight() : "");
        etWeight.setText(weightText);
        String heightText = "height: " + (null != vitalSign.getHeight() ? vitalSign.getHeight() : "");
        etHeight.setText(heightText);

        String[] bloodPressure = vitalSign.getBloodPressure().split(",");
        String systolicText = "systolic: " + (null != bloodPressure[0] ? bloodPressure[0] : "");
        etSystolic.setText(systolicText);
        String diastolicText = "diastolic: " + (null != bloodPressure[1] ? bloodPressure[1] : "");
        etDiastolic.setText(diastolicText);
        String bloodGlucoseText = "bloodGlucose: " + (null != vitalSign.getBloodGlucose() ? vitalSign.getBloodGlucose() : "");
        etBloodGlucose.setText(bloodGlucoseText);
        String heartRateText = "heartRate: " + (null != vitalSign.getHeartRate() ? vitalSign.getHeartRate() : "");
        etHeartRate.setText(heartRateText);
        String bodyTemperatureText = "bodyTemperature: " + (null != vitalSign.getBodyTemperature() ? vitalSign.getBodyTemperature() : "");
        etBodyTemperature.setText(bodyTemperatureText);

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
