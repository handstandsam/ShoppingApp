package com.handstandsam.shoppingapp.di

import android.content.Context
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.CheckoutCart
import com.handstandsam.shoppingapp.repository.SessionManager

interface SessionGraph {
    val sessionManager: SessionManager
    val checkoutCart: CheckoutCart
    val userPreferences: UserPreferences
    val appContext: Context
}

class SessionGraphImpl(
    override val appContext: Context
) : SessionGraph {

    override val checkoutCart = CheckoutCart()

    override val userPreferences = UserPreferences(appContext)

    override val sessionManager = SessionManager(checkoutCart, userPreferences)
}