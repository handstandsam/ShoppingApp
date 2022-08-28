package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.cart.SessionManager
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

class AndroidSessionManager(
    private val cart: ShoppingCart,
    private val userPreferences: UserPreferences
) : SessionManager {

    override val currentUser = MutableStateFlow(userPreferences.getRememberedUser())

    override fun updateCurrentUser(user: User?) {
        Timber.d("setCurrentUser: $user")
        if (userPreferences.rememberMe) {
            // Save to disk if we want to remember them
            userPreferences.updateRememberedUser(user)
        }
        currentUser.value = user
    }

    override val isLoggedIn: Boolean
        get() {
            val loggedIn = currentUser.value != null
            Timber.d("isLoggedIn: $loggedIn")
            return loggedIn
        }

    override suspend fun logout() {
        currentUser.value = null
        userPreferences.updateRememberedUser(null)
        cart.empty()
    }
}
