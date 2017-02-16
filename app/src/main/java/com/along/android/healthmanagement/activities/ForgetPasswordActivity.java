package com.along.android.healthmanagement.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText mEmailEditText;
    Button mForgetPasswordContinueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mForgetPasswordContinueButton = (Button) findViewById(R.id.forget_password_continue);
        mEmailEditText = (EditText) findViewById(R.id.email_edit_text);

        mForgetPasswordContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = EntityManager.findOneBy(User.class, "email = ?", mEmailEditText.getText().toString());

                if (user != null) {
                    String email = user.getEmail();
                    //todo send temporary password to the input email and reset the password
//                    MailHelper.sendEmail();
                    Toast.makeText(ForgetPasswordActivity.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Email doesn't exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
