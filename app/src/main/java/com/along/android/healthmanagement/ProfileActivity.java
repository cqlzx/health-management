package com.along.android.healthmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import DBManager.DatabaseHelper;

/**
 * Created by renxiaolei on 2/14/16.
 */

public class ProfileActivity extends AppCompatActivity {
    // display user informations



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Profile");

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

//        DatabaseHelper helper = new DatabaseHelper(this);
//
//        HashMap<String, String> mapList = helper.searchUserInfo("xiaolei"); //
//        TextView tvUsername=(TextView)findViewById(R.id.username);
//        String testS=mapList.get("password");
//        tvUsername.setText(testS);
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
