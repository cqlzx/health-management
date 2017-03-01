package com.along.android.healthmanagement.helpers;

import android.content.Context;
import android.text.style.LineHeightSpan;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isEmpty(User user, Context context) {
        Boolean bool = true;

        if (user.getEmail().isEmpty()) {
            Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_LONG).show();
            bool = false;

        } else if (user.getPassword().isEmpty()) {
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_LONG).show();
            bool = false;
        } else if (user.getRealname().isEmpty()) {
            Toast.makeText(context, "Full name cannot be empty", Toast.LENGTH_LONG).show();
            bool = false;
        } else if (user.getGender().isEmpty()) {
            Toast.makeText(context, "Gender cannot be empty", Toast.LENGTH_LONG).show();
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

    public static boolean isUserExsist(String email, Context context) {
        Boolean bool = true;
        User user = EntityManager.findOneBy(User.class, "email = ?", email);
        if (user != null) {
            Toast.makeText(context, "Email exists", Toast.LENGTH_LONG).show();
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
            Toast.makeText(context, context.getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
            bool = false;
        }

        return bool;
    }

    public static boolean isNumeric(String age, String weight, Context context) {
        Boolean bool = true;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isAgeNum = pattern.matcher(age);
        Matcher isWeightNum = pattern.matcher(weight);
        if (!isAgeNum.matches()) {
            Toast.makeText(context, "invalid age", Toast.LENGTH_LONG).show();
            bool = false;
        } else if(!isWeightNum.matches()){
            Toast.makeText(context, "invalid weight", Toast.LENGTH_LONG).show();
            bool = false;
        }
        return bool;
    }
    public static boolean isValidPhonenumber(String phonenumber, Context context) {
        Boolean bool = true;
        Pattern regex = Pattern.compile("[0-9]*");
        Matcher matcher = regex.matcher(phonenumber);
        if (phonenumber.length()!=0 && (!matcher.matches() || phonenumber.length()!=10)) {
            Toast.makeText(context, "invalid phone number", Toast.LENGTH_LONG).show();
            bool = false;
        }

        return bool;
    }

    public static boolean isValidPassword(String password, Context context) {
        Boolean bool = true;
        if (password.length() < 8) {
            Toast.makeText(context, "password cannot less than 8 character", Toast.LENGTH_LONG).show();
            bool = false;
        }

        return bool;
    }
}
