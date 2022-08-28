package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.User
import kotlinx.coroutines.flow.MutableStateFlow

interface SessionManager {
    val currentUser: MutableStateFlow<User?>
    val isLoggedIn: Boolean

    fun updateCurrentUser(user: User?)

    suspend fun logout()
}