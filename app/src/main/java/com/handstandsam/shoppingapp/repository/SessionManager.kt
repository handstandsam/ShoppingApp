package com.handstandsam.shoppingapp.repository


import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

class SessionManager(
    private val cart: ShoppingCart,
    private val userPreferences: UserPreferences
) {

    val currentUser = MutableStateFlow(userPreferences.getRememberedUser())

    fun updateCurrentUser(user: User?) {
        Timber.d("setCurrentUser: $user")
        if (userPreferences.rememberMe) {
            // Save to disk if we want to remember them
            userPreferences.updateRememberedUser(user)
        }
        currentUser.value = user
    }

    val isLoggedIn: Boolean
        get() {
            val loggedIn = currentUser.value != null
            Timber.d("isLoggedIn: $loggedIn")
            return loggedIn
        }

    suspend fun logout() {
        currentUser.value = null
        userPreferences.updateRememberedUser(null)
        cart.empty()
    }
}
