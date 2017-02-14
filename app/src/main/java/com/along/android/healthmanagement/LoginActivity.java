package com.along.android.healthmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DBManager.DatabaseHelper;

/**
 * Created by wilberhu on 2/14/17.
 */

public class LoginActivity extends AppCompatActivity{

    DatabaseHelper helper = new DatabaseHelper(this);

    EditText etUsername, etPassword;
    Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                String dbpassword = helper.searchPassword(username);

                if (password.equals(dbpassword)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //intent.setClass(LoginActivity.this,MainActivity.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);
                } else {
                    Toast temp = Toast.makeText(LoginActivity.this, "Username and password don't match", Toast.LENGTH_SHORT);
                    temp.show();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
