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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.SessionData;
import com.along.android.healthmanagement.helpers.Validation;

public class EditProfileActivity extends AppCompatActivity {


    private SessionData sessionData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("EditProfile");

        // button
        Button btn_saveprofile;
        btn_saveprofile = (Button) findViewById(R.id.btn_editprofile);
        //editview
        EditText editName = (EditText) findViewById(R.id.editT_name);
        EditText editEmail = (EditText) findViewById(R.id.editT_email);
        EditText editPassword1 = (EditText) findViewById(R.id.editT_password1);
        EditText editPassword2 = (EditText) findViewById(R.id.editT_password2);
        EditText editAge = (EditText) findViewById(R.id.editT_age);
        EditText editHeight = (EditText) findViewById(R.id.editT_height);
        EditText editWeight = (EditText) findViewById(R.id.editT_weight);
        EditText editPhone = (EditText) findViewById(R.id.editT_phoneNumber);
        // edit RadioButton
        RadioGroup radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);

        // sharePreferences
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long userIdS = sp.getLong("uid", 0);
        // user
        User user = EntityManager.findById(User.class, userIdS);
        if(user != null){
            editName.setText(user.getUsername());
            editEmail.setText(user.getEmail());
            editAge.setText(user.getAge());
            editHeight.setText(user.getHeight());
            editWeight.setText(user.getWeight());
            editPhone.setText(user.getPhonenumber());

//            radioGroupGender

        }

        // click
        btn_saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save information and turn back
//            int selectedId = radioGroupGender.getCheckedRadioButtonId();
//                RadioButton radioButtonGender = (RadioButton) findViewById(selectedId);
//            user.setGender(radioButtonGender.getText().toString());
                User user = new User();

                user.setUsername(editName.getText().toString());
                user.setPassword(editPassword1.getText().toString());
                user.setEmail(editEmail.getText().toString());
//                user.setGender(radioGroupGender.getText().toString());
                user.setAge(editAge.getText().toString());
                user.setPhonenumber(editPhone.getText().toString());
                user.setWeight(editWeight.getText().toString());
                user.setHeight(editHeight.getText().toString());

                if (Validation.isEmpty(user, EditProfileActivity.this) &&
                        Validation.isPasswordMatch(editPassword1.getText().toString(), editPassword2.getText().toString(), EditProfileActivity.this) &&
                        Validation.isValidEmail(user.getEmail(), EditProfileActivity.this)) {
                    //helper.insertContact(user);

                    try{
                        user.save();
                        // sql
                        EditProfileActivity.this.finish();
                    }catch (Exception e){
                        Toast.makeText(EditProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
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
