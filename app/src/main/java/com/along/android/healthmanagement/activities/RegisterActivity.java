package com.along.android.healthmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.Validation;

import DBManager.DatabaseHelper;


public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper helper = new DatabaseHelper(this);

    private RadioGroup radioGroupGender;
    private RadioButton radioButtonGender;
    EditText etUsername, etPassword, etConfirmpassword, etEmail, etRealname, etAge, etPhonenumber, etWeight, etHeight;
    Button btn_register, btn_cancel;

    //EditText[] etUserinfo = {etUsername, etPassword, etEmail, etRealname, etGender, etAge, etPhonenumber, etWeight, etHeight};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("HealthManagement");

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmpassword = (EditText) findViewById(R.id.etConfirmpassword);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etRealname = (EditText) findViewById(R.id.etRealname);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhonenumber = (EditText) findViewById(R.id.etPhonenumber);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                radioButtonGender = (RadioButton) findViewById(selectedId);

                User user = new User();

                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setRealname(etRealname.getText().toString());
                user.setGender(radioButtonGender.getText().toString());
                user.setAge(etAge.getText().toString());
                user.setPhonenumber(etPhonenumber.getText().toString());
                user.setWeight(etWeight.getText().toString());
                user.setHeight(etHeight.getText().toString());

            /*if(userBean.isValid(userBean.getUsername(),userBean.getPassword(),RegisterActivity.this)){
                User user = new User();
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                helper.insertContact(user);
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, MainActivity.class);
                startActivity(intent);

            }*/
                if (Validation.isEmpty(user, RegisterActivity.this) &&
                        Validation.isPasswordMatch(etPassword.getText().toString(), etConfirmpassword.getText().toString(), RegisterActivity.this)) {
                    helper.insertContact(user);
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                }

            }
        });

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
