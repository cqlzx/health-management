package com.along.android.healthmanagement;

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


    EditText etName,etPassword;
    Button btn_register;
    UserBean userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText)findViewById(R.id.etName);
        etPassword = (EditText)findViewById(R.id.etPassword);

        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBean = new UserBean();
                userBean.setUsername(etName.getText().toString());
                userBean.setPassword(etPassword.getText().toString());
                if(userBean.isValid(userBean.getUsername(),userBean.getPassword(),RegisterActivity.this)){
                    /*Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);*/
                    UserBean c = new UserBean();
                    c.setUsername(etName.getText().toString());
                    c.setPassword(etPassword.getText().toString());
                    helper.insertContact(c);

                }



            }
        });
    }
}
