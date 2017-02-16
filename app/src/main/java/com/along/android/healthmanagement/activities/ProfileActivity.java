package com.along.android.healthmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Profile");
        // sharePreferences
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long userIdS = sp.getLong("uid", 0);
        String userNameS = sp.getString("username", null);

        // name & email
        TextView userNameText = (TextView) findViewById(R.id.username);
        TextView emailText = (TextView) findViewById(R.id.email);
        if (userNameS != null) {
            userNameText.setText(userNameS);
        }

        // user informations
        TextView ageText = (TextView) findViewById(R.id.age);
        TextView genderText = (TextView) findViewById(R.id.gender);
        TextView heightText = (TextView) findViewById(R.id.height);
        TextView weightText = (TextView) findViewById(R.id.weight);
        TextView phoneText = (TextView) findViewById(R.id.phone);

        User user = EntityManager.findById(User.class, userIdS);
        if(user != null){
            emailText.setText(user.getEmail());
            Log.d("--email--->>>>>>>", user.getEmail());

            // age
            if (user.getAge() != null && !user.getAge().equals("")){
                ageText.setText(user.getAge());
            }else {
                LinearLayout llAge = (LinearLayout) findViewById(R.id.llAge);
                llAge.setVisibility(View.GONE);
            }
            // gender
            if (user.getGender() != null && !user.getGender().equals("")){
                genderText.setText(user.getGender());
            }else {
                LinearLayout llGender = (LinearLayout) findViewById(R.id.llGender);
                llGender.setVisibility(View.GONE);
            }
            //Height
            if (user.getHeight() != null && !user.getHeight().equals("")){
                heightText.setText(user.getHeight());
            }else {
                LinearLayout llHeight = (LinearLayout) findViewById(R.id.llHeight);
                llHeight.setVisibility(View.GONE);
            }
            //Weight
            if (user.getWeight() != null && !user.getWeight().equals("")){
                weightText.setText(user.getWeight());
            }else {
                LinearLayout llWeight = (LinearLayout) findViewById(R.id.llWeight);
                llWeight.setVisibility(View.GONE);
            }
            //Phone
            if (user.getPhonenumber() != null && !user.getPhonenumber().equals("")){
                phoneText.setText(user.getPhonenumber());
            }else {
                LinearLayout llPhone = (LinearLayout) findViewById(R.id.llPhone);
                llPhone.setVisibility(View.GONE);
            }

        }

//        googleSignIn = (Button) findViewById(R.id.btn_google_sign_in);
//        forgetPassword = (Button) findViewById(R.id.btn_forget_password);

        // button
        Button btn_editprofile;
        btn_editprofile = (Button) findViewById(R.id.btn_editprofile);

        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
