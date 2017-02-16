package com.along.android.healthmanagement.activities;

import android.os.Bundle;

import com.along.android.healthmanagement.R;

public class VitalSignsActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs);
        super.onCreateDrawer();
    }
}
