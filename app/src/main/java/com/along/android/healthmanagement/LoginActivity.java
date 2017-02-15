package com.along.android.healthmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DBManager.DatabaseHelper;

/**
 * Created by wilberhu on 2/14/17.
 */

public class LoginActivity extends BasicActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    EditText etUsername, etPassword;
    Button login, register, home, googleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        home = (Button) findViewById(R.id.btn_home);
        googleSignIn = (Button) findViewById(R.id.btn_google_sign_in);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                String dbpassword = helper.searchPassword(username);

                if (password.equals(dbpassword)) {
                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    //intent.setClass(LoginActivity.this,MainActivity.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);
                } else {
                    Toast temp = Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT);
                    temp.show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
}
