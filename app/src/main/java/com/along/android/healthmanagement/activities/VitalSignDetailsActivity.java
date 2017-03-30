package com.along.android.healthmanagement.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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

        String selectedVitalSignItemDate = getIntent().getStringExtra("selectedVitalSignItemDate");
        TextView tvVitalSignDetailsDate = (TextView) findViewById(R.id.tvVitalSignDetailsDate);
        tvVitalSignDetailsDate.setText(selectedVitalSignItemDate);

        TextView etWeight = (TextView) findViewById(R.id.tvWeight);
        TextView etHeight = (TextView) findViewById(R.id.tvHeight);
        TextView etBloodPressure = (TextView) findViewById(R.id.tvBloodPressure);
        TextView etBloodGlucose = (TextView) findViewById(R.id.tvBloodGlucose);
        TextView etHeartRate = (TextView) findViewById(R.id.tvHeartRate);
        TextView etBodyTemperature = (TextView) findViewById(R.id.tvBodyTemperature);

        LinearLayout llWeigth = (LinearLayout) findViewById(R.id.llWeight);
        LinearLayout llHeight = (LinearLayout) findViewById(R.id.llHeight);
        LinearLayout llBloodPressure = (LinearLayout) findViewById(R.id.llBloodPressure);
        LinearLayout llBloodGlucose = (LinearLayout) findViewById(R.id.llBloodGlucose);
        LinearLayout llHeartRate = (LinearLayout) findViewById(R.id.llHeartRate);
        LinearLayout llBodyTemperature = (LinearLayout) findViewById(R.id.llBodyTemperature);

        View vWeightSeparator = findViewById(R.id.vWeightSeparator);
        View vHeightSeparator = findViewById(R.id.vHeightSeparator);
        View vBloodPressureSeparator = findViewById(R.id.vBloodPressureSeparator);
        View vBloodGlucoseSeparator = findViewById(R.id.vBloodGlucoseSeparator);
        View vHeartRateSeparator = findViewById(R.id.vHeartRateSeparator);
        View vBodyTemperatureSeparator = findViewById(R.id.vBodyTemperatureSeparator);

        if (null != vitalSign.getWeight() && !"".equals(vitalSign.getWeight())) {
            String weightText = vitalSign.getWeight() + " lb";
            etWeight.setText(weightText);
            llWeigth.setVisibility(View.VISIBLE);
            vWeightSeparator.setVisibility(View.VISIBLE);
        } else {
            llWeigth.setVisibility(View.GONE);
            vWeightSeparator.setVisibility(View.GONE);
        }

        if (null != vitalSign.getHeight() && !"".equals(vitalSign.getHeight())) {
            String heightText = vitalSign.getHeight() + " in";
            etHeight.setText(heightText);
            llHeight.setVisibility(View.VISIBLE);
            vHeightSeparator.setVisibility(View.VISIBLE);
        } else {
            llHeight.setVisibility(View.GONE);
            vHeightSeparator.setVisibility(View.GONE);
        }

        if (null != vitalSign.getBloodPressure() && !"".equals(vitalSign.getBloodPressure())) {
            String[] bloodPressure = vitalSign.getBloodPressure().split(",");
            String bloodPreessureText = "";
            if (bloodPressure.length == 1) {
                bloodPreessureText = bloodPressure[0] + " mm Hg";
            } else if (bloodPressure.length == 2) {
                bloodPreessureText = bloodPressure[0] + " - " + bloodPressure[1] + " mm Hg";
            }
            etBloodPressure.setText(bloodPreessureText);
            llBloodPressure.setVisibility(View.VISIBLE);
            vBloodPressureSeparator.setVisibility(View.VISIBLE);
        } else {
            llBloodPressure.setVisibility(View.GONE);
            vBloodPressureSeparator.setVisibility(View.GONE);
        }

        if (null != vitalSign.getBloodGlucose() && !"".equals(vitalSign.getBloodGlucose())) {
            String bloodGlucoseText = (null != vitalSign.getBloodGlucose() ? vitalSign.getBloodGlucose() + " mg/dl" : "");
            etBloodGlucose.setText(bloodGlucoseText);
            llBloodGlucose.setVisibility(View.VISIBLE);
            vBloodGlucoseSeparator.setVisibility(View.VISIBLE);
        } else {
            llBloodGlucose.setVisibility(View.GONE);
            vBloodGlucoseSeparator.setVisibility(View.GONE);
        }

        if (null != vitalSign.getHeartRate() && !"".equals(vitalSign.getHeartRate())) {
            String heartRateText = (null != vitalSign.getHeartRate() ? vitalSign.getHeartRate() + " bmp" : "");
            etHeartRate.setText(heartRateText);
            llHeartRate.setVisibility(View.VISIBLE);
            vHeartRateSeparator.setVisibility(View.VISIBLE);
        } else {
            llHeartRate.setVisibility(View.GONE);
            vHeartRateSeparator.setVisibility(View.GONE);
        }

        if (null != vitalSign.getBodyTemperature() && !"".equals(vitalSign.getBodyTemperature())) {
            String bodyTemperatureText = (null != vitalSign.getBodyTemperature() ? vitalSign.getBodyTemperature() + " °F" : "");
            etBodyTemperature.setText(bodyTemperatureText);
            llBodyTemperature.setVisibility(View.VISIBLE);
            vBodyTemperatureSeparator.setVisibility(View.VISIBLE);
        } else {
            llBodyTemperature.setVisibility(View.GONE);
            vBodyTemperatureSeparator.setVisibility(View.GONE);
        }

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
