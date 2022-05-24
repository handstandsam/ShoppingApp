package com.handstandsam.shoppingapp.cart

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * [Room] database definition
 */
@Database(entities = [RoomItemInCartEntity::class], version = 1, exportSchema = false)
abstract class RoomItemInCartDatabase : RoomDatabase() {
    abstract fun itemInCartDao(): RoomItemInCartDao
}