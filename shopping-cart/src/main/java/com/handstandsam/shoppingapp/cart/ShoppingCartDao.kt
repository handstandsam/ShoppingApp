package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.Flow

/**
 * All actions to access and modify our [ShoppingCart] database.
 */
interface ShoppingCartDao {

    suspend fun selectAll(): List<ItemWithQuantity>

    val selectAllStream: Flow<List<ItemWithQuantity>>

    suspend fun findByLabel(label: String): ItemWithQuantity?

    suspend fun upsert(itemWithQuantity: ItemWithQuantity)

    suspend fun remove(itemWithQuantity: ItemWithQuantity)

    suspend fun empty()
}