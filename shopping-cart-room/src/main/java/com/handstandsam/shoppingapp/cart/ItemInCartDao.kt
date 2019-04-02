package com.handstandsam.shoppingapp.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemInCartDao {
    @Query("SELECT * FROM ItemInCart ORDER BY label")
    fun selectAll(): List<ItemInCart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg itemInCart: ItemInCart)

    @Query("SELECT * FROM ItemInCart WHERE label = :label LIMIT 1")
    fun findByLabel(label: String): ItemInCart?

    @Delete
    fun remove(user: ItemInCart)

    @Query("DELETE FROM ItemInCart")
    fun empty()
}