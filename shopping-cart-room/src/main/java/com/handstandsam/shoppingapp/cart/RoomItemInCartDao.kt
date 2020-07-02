package com.handstandsam.shoppingapp.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room

/**
 * [Dao] definition for [Room] to generate code to persist [RoomItemInCartEntity] objects in the database
 */
@Dao
interface RoomItemInCartDao {
    @Query("SELECT * FROM RoomItemInCartEntity ORDER BY label")
    fun selectAllStream(): LiveData<List<RoomItemInCartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg itemInCartEntity: RoomItemInCartEntity)

    @Query("SELECT * FROM RoomItemInCartEntity WHERE label = :label LIMIT 1")
    fun findByLabel(label: String): RoomItemInCartEntity?

    @Delete
    fun remove(user: RoomItemInCartEntity)

    @Query("DELETE FROM RoomItemInCartEntity")
    fun empty()
}