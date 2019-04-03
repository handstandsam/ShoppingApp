package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.channels.ReceiveChannel

interface ShoppingCart {
    suspend fun addItem(item: Item)
    suspend fun removeItem(item: Item)
    suspend fun itemsInCart(): List<ItemWithQuantity>
    fun itemsInCartChannel(): ReceiveChannel<List<ItemWithQuantity>>
    suspend fun empty()
}