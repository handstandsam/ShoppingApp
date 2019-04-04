package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber

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
        if (foundItem == null) {
            Timber.w("Item didn't exist.  This must have been called by error.")
        } else {
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
    suspend fun itemsInCart(): List<ItemWithQuantity> {
        return shoppingCartDao.selectAll()
    }

    /**
     * Exposes a [ReceiveChannel] which can be subscribed to, to get Cart updates
     */
    suspend fun itemsInCartChannel(): ReceiveChannel<List<ItemWithQuantity>> {
        return shoppingCartDao.selectAllStream()
    }

    /**
     * Empties the [ShoppingCart]
     */
    suspend fun empty() {
        shoppingCartDao.empty()
    }
}