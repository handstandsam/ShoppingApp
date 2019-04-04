package com.handstandsam.shoppingapp.di

import android.content.Context
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.cart.SqlDelightShoppingCartDao
import com.handstandsam.shoppingapp.cart.sqldelight.Database
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.SessionManager
import com.squareup.sqldelight.android.AndroidSqliteDriver

interface SessionGraph {
    val sessionManager: SessionManager
    val shoppingCart: ShoppingCart
    val userPreferences: UserPreferences
}

class SessionGraphImpl(
    appContext: Context
) : SessionGraph {

    override val shoppingCart: ShoppingCart = ShoppingCart(
        SqlDelightShoppingCartDao(
            AndroidSqliteDriver(
                schema = Database.Schema,
                context = appContext,
                name = "cart_sqldelight.db"
            )
        )
    )

    override val userPreferences = UserPreferences(appContext)

    override val sessionManager = SessionManager(shoppingCart, userPreferences)
}