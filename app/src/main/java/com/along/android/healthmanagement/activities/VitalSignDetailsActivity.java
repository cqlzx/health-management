package com.along.android.healthmanagement.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.VitalSign;
import com.along.android.healthmanagement.helpers.EntityManager;

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
        TextView etBloodPressure = (TextView) findViewById(R.id.tvBloodPressure);
        TextView etBloodGlucose = (TextView) findViewById(R.id.tvBloodGlucose);
        TextView etHeartRate = (TextView) findViewById(R.id.tvHeartRate);
        TextView etBodyTemperature = (TextView) findViewById(R.id.tvBodyTemperature);


        String weightText = (null != vitalSign.getWeight() ? vitalSign.getWeight() + " lb" : "" );
        etWeight.setText(weightText);
        String heightText = (null != vitalSign.getHeight() ? "height: " + vitalSign.getHeight() + " in" : "");
        etHeight.setText(heightText);

        String[] bloodPressure = vitalSign.getBloodPressure().split(",");
        String bloodPreessureText = "";
        if (bloodPressure.length == 1) {
            bloodPreessureText = (null != bloodPressure ? bloodPressure[0] + " mm Hg" : "");
        } else if (bloodPressure.length == 2) {
            bloodPreessureText = (null != bloodPressure ? bloodPressure[0] + " - " + bloodPressure[1] + " mm Hg" : "");
        }

        etBloodPressure.setText(bloodPreessureText);
        String bloodGlucoseText = (null != vitalSign.getBloodGlucose() ? vitalSign.getBloodGlucose() + " mg/dl" : "");
        etBloodGlucose.setText(bloodGlucoseText);
        String heartRateText = (null != vitalSign.getHeartRate() ? vitalSign.getHeartRate() + " bmp" : "");
        etHeartRate.setText(heartRateText);
        String bodyTemperatureText = (null != vitalSign.getBodyTemperature() ? vitalSign.getBodyTemperature() + " °F" : "");
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
