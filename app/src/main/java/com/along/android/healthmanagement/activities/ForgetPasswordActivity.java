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
import com.along.android.healthmanagement.helpers.MailHelper;

import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText mEmailEditText;
    Button mForgetPasswordContinueButton;
    private int passwordLength = 8;

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
                    String subject = "HealthManagement Support";
                    String content = makeEmailContent(user.resetPassword(passwordLength));
                    new MailHelper().execute(email, subject, content);
                    Toast.makeText(ForgetPasswordActivity.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Email doesn't exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String makeEmailContent(String password) {
        return "Dear Friend," +
                "<br /><br />Thanks for using HealthManagement. Your password has been reset!" +
                "<br /><br />Your password is : <b>" + password + "</b>" +
                "<br /><br /><b>Notice: </b> This is a temporary password and will expire in 1 hour.";
    }
}
