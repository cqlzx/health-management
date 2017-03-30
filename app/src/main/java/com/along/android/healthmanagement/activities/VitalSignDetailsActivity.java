package com.along.android.healthmanagement.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.VitalSign;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.Calendar;
import java.util.List;

public class VitalSignDetailsActivity extends AppCompatActivity {


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
//        TextView etDiastolic = (TextView) findViewById(R.id.tvDiastolic);
        TextView etBloodGlucose = (TextView) findViewById(R.id.tvBloodGlucose);
        TextView etHeartRate = (TextView) findViewById(R.id.tvHeartRate);
        TextView etBodyTemperature = (TextView) findViewById(R.id.tvBodyTemperature);


        String weightText = (null != vitalSign.getWeight() ? vitalSign.getWeight() + " lb" : "" );
        etWeight.setText(weightText);
        String heightText = (null != vitalSign.getHeight() ? vitalSign.getHeight() + " in" : "");
        etHeight.setText(heightText);

        String[] bloodPressure = vitalSign.getBloodPressure().split(",");
        String bloodPreessureText = (null != bloodPressure ? bloodPressure[0] + " - " + bloodPressure[1] + " mm Hg" : "");
        etBloodPressure.setText(bloodPreessureText);
        String bloodGlucoseText = (null != vitalSign.getBloodGlucose() ? vitalSign.getBloodGlucose() + " mg/dl" : "");
        etBloodGlucose.setText(bloodGlucoseText);
        String heartRateText = (null != vitalSign.getHeartRate() ? vitalSign.getHeartRate() + " bmp" : "");
        etHeartRate.setText(heartRateText);
        String bodyTemperatureText = (null != vitalSign.getBodyTemperature() ? vitalSign.getBodyTemperature() + " °F" : "");
        etBodyTemperature.setText(bodyTemperatureText);



        View fab1 = findViewById(R.id.card_view1);
        View fab2 = findViewById(R.id.card_view2);
        View fab3 = findViewById(R.id.card_view3);
        View fab4 = findViewById(R.id.card_view4);
        View fab5 = findViewById(R.id.card_view5);
        View fab6 = findViewById(R.id.card_view6);
        ScaleAnimation animation = new ScaleAnimation(0f, 1.0f, 0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(800);
        fab1.startAnimation(animation);
        fab2.startAnimation(animation);
        fab3.startAnimation(animation);
        fab4.startAnimation(animation);
        fab5.startAnimation(animation);
        fab6.startAnimation(animation);

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
