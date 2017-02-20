package com.along.android.healthmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.Date;

public class LoginActivity extends BasicActivity {

    //DatabaseHelper helper = new DatabaseHelper(this);

    EditText etUsername, etPassword;
    Button login, register, googleSignIn, forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        googleSignIn = (Button) findViewById(R.id.btn_google_sign_in);
        forgetPassword = (Button) findViewById(R.id.btn_forget_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User user = EntityManager.findOneBy(User.class, "username = ?", username);

                if(user != null) {
                    if(user.getPassword().equals(password)) {
                        if (user.getPasswordExpirationTime() == 0 || user.getPasswordExpirationTime() > new Date().getTime()) {
                            SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
                            sp.edit().putString("username",username).apply();
                            sp.edit().putLong("uid",user.getId()).apply();

                            // Remove below code if not used
                            Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                            //intent.setClass(LoginActivity.this,MainActivity.class);
                            intent.putExtra("Username", username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Password has expired", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

                //String dbpassword = helper.searchPassword(username);
//                String dbpassword;
//                if (password.equals(dbpassword)) {
//                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
//                    //intent.setClass(LoginActivity.this,MainActivity.class);
//                    intent.putExtra("Username", username);
//                    startActivity(intent);
//                } else {
//                    Toast temp = Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT);
//                    temp.show();
//                }
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

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
