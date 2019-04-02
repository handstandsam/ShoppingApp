package com.handstandsam.shoppingapp.cart

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemInCart::class], version = 1)
abstract class ItemInCartDatabase : RoomDatabase() {
    abstract fun itemInCartDao(): ItemInCartDao
}