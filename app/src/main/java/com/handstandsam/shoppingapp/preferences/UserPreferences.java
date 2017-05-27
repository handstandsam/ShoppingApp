package com.handstandsam.shoppingapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.handstandsam.shoppingapp.models.User;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import timber.log.Timber;

public class UserPreferences {

    Moshi moshi = new Moshi.Builder().build();

    public static final String REMEMBER_ME = "REMEMBER_ME";

    public static final String LAST_LOGGED_IN_USERNAME = "LAST_LOGGED_IN_USERNAME";

    public static final String CURRENT_USER = "CURRENT_USER";

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
        sharedPreferences.edit().putString(CURRENT_USER, moshi.adapter(User.class).toJson(user)).apply();
    }

    public User getCurrentUser() {
        String json = sharedPreferences.getString(CURRENT_USER, null);
        if (json != null) {
            try {
                return moshi.adapter(User.class).fromJson(json);
            } catch (IOException e) {
                Timber.w(e);
            }
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
