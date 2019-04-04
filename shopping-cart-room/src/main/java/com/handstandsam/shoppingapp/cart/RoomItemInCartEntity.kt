package com.handstandsam.shoppingapp.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room

/**
 * Describes an [Entity] (or object) that will be saved in the [Room] database
 */
@Entity
data class RoomItemInCartEntity(
    @PrimaryKey
    val label: String,
    val image: String,
    val link: String?,
    val quantity: Long
)