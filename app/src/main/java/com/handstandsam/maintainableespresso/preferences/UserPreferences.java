package com.handstandsam.maintainableespresso.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handstandsam.maintainableespresso.models.User;

public class UserPreferences {

    public static final String REMEMBER_ME = "REMEMBER_ME";

    public static final String LAST_LOGGED_IN_USERNAME = "LAST_LOGGED_IN_USERNAME";

    public static final String CURRENT_USER = "CURRENT_USER";

    private static Gson gson = new GsonBuilder().create();

    private final SharedPreferences sharedPreferences;

    public UserPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public boolean getRememberMe() {
        boolean value = sharedPreferences.getBoolean(REMEMBER_ME, false);
        return value;
    }

    public String getLastLoggedInUsername() {
        String value = sharedPreferences.getString(LAST_LOGGED_IN_USERNAME, null);
        return value;
    }

    public void setLastLoggedInUsername(String value) {
        sharedPreferences.edit().putString(LAST_LOGGED_IN_USERNAME, value).apply();
    }

    public void setCurrentUser(User user) {
        sharedPreferences.edit().putString(CURRENT_USER, gson.toJson(user)).apply();
    }

    public User getCurrentUser() {
        String json = sharedPreferences.getString(CURRENT_USER, null);
        if (json != null) {
            return gson.fromJson(json, User.class);
        }
        return null;
    }

    public void setRememberMe(boolean rememberMe, String username) {
        sharedPreferences.edit().putBoolean(REMEMBER_ME, rememberMe).apply();
        if (rememberMe) {
            setLastLoggedInUsername(username);
        } else {
            setLastLoggedInUsername(null);
        }
    }
}
