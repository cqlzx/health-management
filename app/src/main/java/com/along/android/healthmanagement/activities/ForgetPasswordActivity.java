package com.along.android.healthmanagement.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;

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
                List<User> users = User.find(User.class, "email = ?", mEmailEditText.getText().toString());

                if(users.size() > 0) {
                    User user = users.get(0);
                    String email = user.getEmail();
                    //todo send temporary password to the input email
                }
            }
        });
    }
}
