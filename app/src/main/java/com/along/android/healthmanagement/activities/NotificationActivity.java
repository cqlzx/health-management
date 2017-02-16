package com.along.android.healthmanagement.activities;

import android.os.Bundle;

import com.along.android.healthmanagement.R;

public class NotificationActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        super.onCreateDrawer();
    }
}
