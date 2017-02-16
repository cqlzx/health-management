package com.along.android.healthmanagement.activities;

import android.os.Bundle;

import com.along.android.healthmanagement.R;

public class MainActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();
        /*String username = getIntent().getStringExtra("Username");
        TextView tv = (TextView)findViewById(R.id.tvUsername);
        tv.setText(username);

        Button btn_profile;
        btn_profile = (Button) findViewById(R.id.btn_profile);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
