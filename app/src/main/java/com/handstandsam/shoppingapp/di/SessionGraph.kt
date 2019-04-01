package com.handstandsam.shoppingapp.di

import android.content.Context
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.cart.ShoppingCartInMemory
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.SessionManager

interface SessionGraph {
    val sessionManager: SessionManager
    val shoppingCart: ShoppingCart
    val userPreferences: UserPreferences
}

class SessionGraphImpl(
    appContext: Context
) : SessionGraph {

    override val shoppingCart: ShoppingCart = ShoppingCartInMemory()

    override val userPreferences = UserPreferences(appContext)

    override val sessionManager = SessionManager(shoppingCart, userPreferences)
}