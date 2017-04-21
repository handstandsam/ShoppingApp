package com.handstandsam.maintainableespresso.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPreferences {

    public static final String REMEMBER_ME = "REMEMBER_ME";

    public static final String LAST_LOGGED_IN_USERNAME = "LAST_LOGGED_IN_USERNAME";

    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";


    private final SharedPreferences sharedPreferences;

    public UserPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public boolean isLoggedIn() {
        boolean value = sharedPreferences.getBoolean(IS_LOGGED_IN, false);
        return value;
    }

    public void setIsLoggedIn(boolean value) {
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, value).apply();
    }

    public boolean getRememberMe() {
        boolean value = sharedPreferences.getBoolean(REMEMBER_ME, false);
        return value;
    }

    public void setRememberMe(boolean value) {
        sharedPreferences.edit().putBoolean(REMEMBER_ME, value).apply();
    }


    public String getLastLoggedInUsername() {
        String value = sharedPreferences.getString(LAST_LOGGED_IN_USERNAME, null);
        return value;
    }

    public void setLastLoggedInUsername(String value) {
        sharedPreferences.edit().putString(LAST_LOGGED_IN_USERNAME, value).apply();
    }

}
