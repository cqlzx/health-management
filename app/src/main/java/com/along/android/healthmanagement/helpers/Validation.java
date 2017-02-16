package com.along.android.healthmanagement.helpers;

import android.content.Context;
import android.widget.Toast;

import com.along.android.healthmanagement.entities.User;

/**
 * Created by wilberhu on 2/15/17.
 */

public class Validation {
    public static boolean isEmpty(User user, Context context) {
        Boolean bool = true;
        if (user.getUsername().isEmpty()) {
            Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_LONG).show();
            bool = false;

        } else if (user.getPassword().isEmpty()) {
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_LONG).show();
            bool = false;
        } else if (user.getEmail().isEmpty()) {
            Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_LONG).show();
            bool = false;
        }

        return bool;
    }
    public static boolean isPasswordMatch(String password, String confirmPassword, Context context) {
        Boolean bool = true;
        if (!password.equals(confirmPassword)) {
            Toast.makeText(context, "The passwords do not match", Toast.LENGTH_LONG).show();
            bool = false;
        }

        return bool;
    }
}
