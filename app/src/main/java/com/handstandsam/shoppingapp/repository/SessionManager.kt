package com.handstandsam.shoppingapp.repository


import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.preferences.UserPreferences

import timber.log.Timber

class SessionManager(private val cart: CheckoutCart, private val userPreferences: UserPreferences) {

    var currentUser: User?
        get() = userPreferences.currentUser
        set(user) {
            Timber.d("setCurrentUser: $user")
            userPreferences.currentUser = user
        }

    val isLoggedIn: Boolean
        get() {
            val loggedIn = currentUser != null
            Timber.d("isLoggedIn: $loggedIn")
            return loggedIn
        }

    fun logout() {
        currentUser = null
        cart.empty()
    }
}
