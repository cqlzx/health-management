package com.along.android.healthmanagement.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by RitenVithlani on 2/15/17.
 */

public class SessionData {

    private SharedPreferences prefs;

    public SessionData(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsername(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public String getUsername() {
        String usename = prefs.getString("usename","");
        return usename;
    }
    public void setEmail(String email) {
        prefs.edit().putString("email", email).commit();
    }

    public String getEmail() {
        String email = prefs.getString("email","");
        return email;
    }
}