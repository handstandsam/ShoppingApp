package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.Flow

/**
 * All actions to access and modify our [ShoppingCart] database.
 */
interface ShoppingCartDao {

    val allItems: Flow<List<ItemWithQuantity>>

    suspend fun findByLabel(label: String): ItemWithQuantity?

    suspend fun upsert(itemWithQuantity: ItemWithQuantity)

    suspend fun remove(itemWithQuantity: ItemWithQuantity)

    suspend fun empty()
}