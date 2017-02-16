package com.along.android.healthmanagement.helpers;

import android.content.Context;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.along.android.healthmanagement.entities.User;

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

    public static boolean isUserExsist(String username, String email, Context context) {
        Boolean bool = true;
        User user = EntityManager.findOneBy(User.class, "username = ?", username);
        if (user != null) {
            Toast.makeText(context, "Username exsists", Toast.LENGTH_LONG).show();
            bool = false;
        }

        user = EntityManager.findOneBy(User.class, "email = ?", email);
        if (user != null) {
            Toast.makeText(context, "Email exsists", Toast.LENGTH_LONG).show();
            bool = false;
        }

        return bool;
    }

    public static boolean isValidEmail(String email, Context context) {
        Boolean bool = true;
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        if (!matcher.matches()) {
            Toast.makeText(context, "The email isn't valid", Toast.LENGTH_LONG).show();
            bool = false;
        }

        return bool;
    }
}
