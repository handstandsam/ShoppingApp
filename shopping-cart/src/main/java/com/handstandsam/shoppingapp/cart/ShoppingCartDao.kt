package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.channels.ReceiveChannel

interface ShoppingCartDao {

    suspend fun selectAll(): List<ItemWithQuantity>

    suspend fun selectAllStream(): ReceiveChannel<List<ItemWithQuantity>>

    suspend fun findByLabel(label: String): ItemWithQuantity?

    suspend fun upsert(itemInCart: ItemWithQuantity)

    suspend fun remove(itemInCart: ItemWithQuantity)

    suspend fun empty()
}