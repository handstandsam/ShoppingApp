package com.handstandsam.shoppingapp.debug

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class DebugPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)


    var isMockMode: Boolean
        get() = sharedPreferences.getBoolean(MOCK_MODE, false)
        set(on) = sharedPreferences.edit().putBoolean(MOCK_MODE, on).apply()

    var isChuckEnabled: Boolean
        get() = sharedPreferences.getBoolean(CHUCK, false)
        set(enabled) = sharedPreferences.edit().putBoolean(CHUCK, enabled).apply()

    companion object {
        val MOCK_MODE = "mock_mode"
        val CHUCK = "chuck"
    }

}
