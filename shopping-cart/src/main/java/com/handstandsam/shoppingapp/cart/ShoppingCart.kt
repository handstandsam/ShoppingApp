package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * Application Logic for interacting with the [ShoppingCartDao]
 */
class ShoppingCart(private val shoppingCartDao: ShoppingCartDao) {
    /**
     * Does an Insert/Update and increments the quantity by 1
     */
    suspend fun incrementItemInCart(item: Item) {
        val itemWithQuantity: ItemWithQuantity =
            shoppingCartDao.findByLabel(item.label) ?: ItemWithQuantity(item, 0)

        val updatedItemWithQuantity =
            itemWithQuantity.copy(quantity = itemWithQuantity.quantity + 1)
        shoppingCartDao.upsert(updatedItemWithQuantity)
    }

    /**
     * Does an Insert/Update and decrements the quantity by 1
     */
    suspend fun decrementItemInCart(item: Item) {
        val foundItem = shoppingCartDao.findByLabel(item.label)
        foundItem?.let {
            val newQuantity = foundItem.quantity - 1
            if (newQuantity <= 0L) {
                shoppingCartDao.remove(foundItem)
            } else {
                shoppingCartDao.upsert(foundItem.copy(quantity = newQuantity))
            }
        }
    }

    /**
     * Exposes a current list of the Items in the Cart
     */
    // Visible for Testing
    suspend fun latestItemsInCart(): List<ItemWithQuantity> {
        return shoppingCartDao.allItems
            .first()
    }

    /**
     * Exposes a reactive stream via a [Flow]
     * which can be subscribed to, to get Shopping Cart updates
     */
    val itemsInCart: Flow<List<ItemWithQuantity>> = shoppingCartDao.allItems

    /**
     * Empties the [ShoppingCart]
     */
    suspend fun empty() {
        shoppingCartDao.empty()
    }
}