package com.handstandsam.shoppingapp.di

import android.content.Context
import androidx.room.Room
import com.handstandsam.shoppingapp.cart.RoomItemInCartDatabase
import com.handstandsam.shoppingapp.cart.RoomShoppingCartDao
import com.handstandsam.shoppingapp.cart.ShoppingCart
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

    /**
     * We will use the [Room] implementation of our [ShoppingCart]
     */
    private val itemInCartDatabase: RoomItemInCartDatabase = Room.databaseBuilder(
        appContext,
        RoomItemInCartDatabase::class.java,
        "cart_room"
    ).build()

    /**
     * Specifically use our [Room] Database
     */
    override val shoppingCart: ShoppingCart = ShoppingCart(RoomShoppingCartDao(itemInCartDatabase))

    override val userPreferences = UserPreferences(appContext)

    override val sessionManager = SessionManager(shoppingCart, userPreferences)
}