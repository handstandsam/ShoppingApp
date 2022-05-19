package com.handstandsam.shoppingapp.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.handstandsam.shoppingapp.models.User
import java.io.IOException
import org.json.JSONObject
import timber.log.Timber

class UserPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    val rememberMe: Boolean
        get() {
            return sharedPreferences.getBoolean(REMEMBER_ME, false)
        }

    var lastLoggedInUsername: String?
        get() {
            return sharedPreferences.getString(LAST_LOGGED_IN_USERNAME, null)
        }
        set(value) = sharedPreferences.edit().putString(LAST_LOGGED_IN_USERNAME, value).apply()

    fun updateRememberedUser(user: User?) {
        sharedPreferences.edit().apply {
            if (user != null) {
                putString(
                    CURRENT_USER,
                    JSONObject()
                        .apply {
                            put("firstname", user.firstname)
                            put("lastname", user.lastname)
                        }
                        .toString()
                )
            } else {
                remove(CURRENT_USER)
            }
        }.apply()
    }

    fun getRememberedUser(): User? {
        val json = sharedPreferences.getString(CURRENT_USER, null)
        if (json != null) {
            try {
                val jsonObject = JSONObject(json)
                return User(
                    firstname = jsonObject.optString("firstname"),
                    lastname = jsonObject.optString("lastname")
                )
            } catch (e: IOException) {
                Timber.w(e)
            }
        }
        return null
    }

    fun setLastLoggedInUsername(rememberMe: Boolean, username: String?) {
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
