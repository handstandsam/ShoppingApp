package com.handstandsam.shoppingapp.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.handstandsam.shoppingapp.models.User
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.io.IOException

class UserPreferences(context: Context) {

    internal var moshi = Moshi.Builder().build()

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    val rememberMe: Boolean
        get() {
            return sharedPreferences.getBoolean(REMEMBER_ME, false)
        }

    var lastLoggedInUsername: String?
        get() {
            return sharedPreferences.getString(LAST_LOGGED_IN_USERNAME, null)
        }
        set(value) = sharedPreferences.edit().putString(LAST_LOGGED_IN_USERNAME, value).apply()

    var currentUser: User?
        get() {
            val json = sharedPreferences.getString(CURRENT_USER, null)
            if (json != null) {
                try {
                    return moshi.adapter(User::class.java).fromJson(json)
                } catch (e: IOException) {
                    Timber.w(e)
                }
            }
            return null
        }
        set(user) = sharedPreferences.edit().putString(CURRENT_USER, moshi.adapter(User::class.java).toJson(user)).apply()

    fun clearRememberMe() {
        setRememberMe(false, null)
        currentUser = null
    }

    fun setRememberMe(rememberMe: Boolean, username: String?) {
        sharedPreferences.edit().putBoolean(REMEMBER_ME, rememberMe).apply()
        if (rememberMe) {
            lastLoggedInUsername = username
        } else {
            lastLoggedInUsername = null
        }
    }

    companion object {

        val REMEMBER_ME = "REMEMBER_ME"

        val LAST_LOGGED_IN_USERNAME = "LAST_LOGGED_IN_USERNAME"

        val CURRENT_USER = "CURRENT_USER"
    }
}
