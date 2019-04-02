package com.handstandsam.shoppingapp.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemInCart(
    @PrimaryKey
    val label: String,
    val image: String,
    val link: String?,
    val quantity: Long
)