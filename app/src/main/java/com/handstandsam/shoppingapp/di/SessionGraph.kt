package com.handstandsam.shoppingapp.di

import android.content.Context
import com.handstandsam.shoppingapp.cart.ActorStateFlowShoppingCartDao
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.cart.ShoppingCartDao
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.AndroidSessionManager

interface SessionGraph {
    val sessionManager: AndroidSessionManager
    val shoppingCart: ShoppingCart
    val userPreferences: UserPreferences
}

class SessionGraphImpl(
    appContext: Context
) : SessionGraph {

    private enum class DatabaseType { IN_MEMORY, ROOM, SQLDELIGHT }

    private val dbType = DatabaseType.IN_MEMORY

    private val shoppingCartDao: ShoppingCartDao = when (dbType) {
        DatabaseType.IN_MEMORY -> {
            ActorStateFlowShoppingCartDao()
        }
        DatabaseType.ROOM -> {
            error("Add the dependency for shopping-room")
//            RoomShoppingCartDao(
//                Room.databaseBuilder(
//                    appContext,
//                    RoomItemInCartDatabase::class.java,
//                    "cart_room.db"
//                ).build()
//            )
        }
        DatabaseType.SQLDELIGHT -> {
            error("Add the dependency for shopping-cartsqldelight")
//            SqlDelightShoppingCartDao(
//                AndroidSqliteDriver(
//                    schema = Database.Schema,
//                    context = appContext,
//                    name = "cart_sqldelight.db"
//                )
//            )
        }
    }

    override val shoppingCart: ShoppingCart = ShoppingCart(shoppingCartDao)

    override val userPreferences = UserPreferences(appContext)

    override val sessionManager = AndroidSessionManager(shoppingCart, userPreferences)
}