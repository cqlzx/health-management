package com.along.android.healthmanagement.activities;

import android.os.Bundle;

import com.along.android.healthmanagement.R;

public class CommunicationActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        super.onCreateDrawer();
    }
}
