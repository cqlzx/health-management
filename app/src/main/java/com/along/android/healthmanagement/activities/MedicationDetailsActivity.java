package com.along.android.healthmanagement.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.along.android.healthmanagement.R;

public class MedicationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String selectedPrescriptionItemId = getIntent().getStringExtra("selectedPrescriptionItemId");

        TextView tv = (TextView) findViewById(R.id.test);
        tv.setText(selectedPrescriptionItemId);
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
