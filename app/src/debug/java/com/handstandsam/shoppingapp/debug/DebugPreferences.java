package com.handstandsam.shoppingapp.debug;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DebugPreferences {

    public static final String MOCK_MODE = "mock_mode";
    public static final String CHUCK = "chuck";

    private final SharedPreferences sharedPreferences;

    public DebugPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public boolean isMockMode() {
        boolean isMock = sharedPreferences.getBoolean(MOCK_MODE, false);
        return isMock;
    }

    public void setMockMode(boolean on) {
        sharedPreferences.edit().putBoolean(MOCK_MODE, on).apply();
    }

    public boolean isChuckEnabled() {
        boolean enabled = sharedPreferences.getBoolean(CHUCK, false);
        return enabled;
    }

    public void setChuckEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(CHUCK, enabled).apply();
    }

}
