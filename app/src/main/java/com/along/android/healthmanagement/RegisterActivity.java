package com.along.android.healthmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Bean.UserBean;
import DBManager.DatabaseHelper;

/**
 * Created by wilberhu on 2/14/17.
 */

public class RegisterActivity extends AppCompatActivity{
    DatabaseHelper helper = new DatabaseHelper(this);


    EditText etUsername,etPassword,etEmail, etRealname, etGender, etAge, etPhonenumber, etWeight, etHeight;
    Button btn_register,btn_cancel;
    UserBean userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);

        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBean = new UserBean();
                userBean.setUsername(etUsername.getText().toString());
                userBean.setPassword(etPassword.getText().toString());
                if(userBean.isValid(userBean.getUsername(),userBean.getPassword(),RegisterActivity.this)){
                    UserBean c = new UserBean();
                    c.setUsername(etUsername.getText().toString());
                    c.setPassword(etPassword.getText().toString());
                    helper.insertContact(c);
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                }



            }
        });
        /*btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
