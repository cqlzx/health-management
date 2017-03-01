package com.along.android.healthmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Validation;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editRealname, editEmail, editPassword1, editPassword2, editAge, editHeight, editWeight, editPhone;
    User user;
    RadioGroup radioGroupGender;
    Button btn_saveprofile, btn_cancel_edit_profile;

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

        btn_saveprofile = (Button) findViewById(R.id.btn_saveprofile);
        btn_cancel_edit_profile = (Button) findViewById(R.id.btn_cancel_edit_profile);
        //editview
        editRealname = (EditText) findViewById(R.id.editT_name);
        editEmail = (EditText) findViewById(R.id.editT_email);
        editPassword1 = (EditText) findViewById(R.id.editT_password1);
        editPassword2 = (EditText) findViewById(R.id.editT_password2);
        editAge = (EditText) findViewById(R.id.editT_age);
        editHeight = (EditText) findViewById(R.id.editT_height);
        editWeight = (EditText) findViewById(R.id.editT_weight);
        editPhone = (EditText) findViewById(R.id.editT_phoneNumber);
        // edit RadioButton
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGenderProfile);

        // sharePreferences
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long userIdS = sp.getLong("uid", 0);
        // user
        user = EntityManager.findById(User.class, userIdS);
        if (user != null) {
            editRealname.setText(user.getRealname());
            editEmail.setText(user.getEmail());
            editAge.setText(user.getAge());
            editHeight.setText(user.getHeight());
            editWeight.setText(user.getWeight());
            editPhone.setText(user.getPhonenumber());
            editPassword1.setText(user.getPassword());
            editPassword2.setText(user.getPassword());

            if(null!= user.getGender()) {
                if (user.getGender().equals("Male")) {
                    radioGroupGender.check(R.id.radioMaleProfile);
                } else if (user.getGender().equals("Female")) {
                    radioGroupGender.check(R.id.radioFemaleProfile);
                }
            }

        }

        // click
        btn_saveprofile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // save information and turn back
//            int selectedId = radioGroupGender.getCheckedRadioButtonId();
//                RadioButton radioButtonGender = (RadioButton) findViewById(selectedId);
//            user.setGender(radioButtonGender.getText().toString());
//                User user = new User();

                //user.setRealname(editRealname.getText().toString());
                user.setPassword(editPassword1.getText().toString());
                user.setEmail(editEmail.getText().toString());
//                user.setGender(radioGroupGender.getText().toString());
                user.setAge(editAge.getText().toString());
                user.setPhonenumber(editPhone.getText().toString());
                user.setWeight(editWeight.getText().toString());
                user.setHeight(editHeight.getText().toString());

                int checkedId = radioGroupGender.getCheckedRadioButtonId();
                RadioButton genderRadio = (RadioButton) findViewById(checkedId);
                user.setGender(genderRadio.getText().toString());

                if (Validation.isEmpty(user, EditProfileActivity.this) &&
                        Validation.isPasswordMatch(editPassword1.getText().toString(), editPassword2.getText().toString(), EditProfileActivity.this) &&
                        Validation.isValidEmail(user.getEmail(), EditProfileActivity.this)) {

                    try {
                        user.save();
                        Intent intent = new Intent();
                        intent.setClass(EditProfileActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(EditProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btn_cancel_edit_profile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(EditProfileActivity.this, ProfileActivity.class);
//                startActivity(intent);
                finish();
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
