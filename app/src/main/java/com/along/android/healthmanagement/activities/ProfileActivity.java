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
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;

public class ProfileActivity extends AppCompatActivity {
    // display user informations


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
        // user
        User user = EntityManager.findById(User.class, userIdS);
        if(user != null){
            emailText.setText(user.getEmail());
            Log.d("--email--->>>>>>>", user.getEmail());
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
