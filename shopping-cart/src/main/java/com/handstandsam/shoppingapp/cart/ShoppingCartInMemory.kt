package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel


class ShoppingCartInMemory : CoroutineScope by CoroutineScope(Dispatchers.Default),
    ShoppingCart {

    private val itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

    private val channel = ConflatedBroadcastChannel(listOf<ItemWithQuantity>())

    override suspend fun empty() {
        itemsInCart.clear()
        sendUpdateChannel()
    }

    override suspend fun addItem(item: Item) {
        val key = item.label
        val value: ItemWithQuantity = itemsInCart[key] ?: ItemWithQuantity(item, 0)
        itemsInCart[key] = value.copy(quantity = value.quantity + 1)
        sendUpdateChannel()
    }

    override suspend fun removeItem(item: Item) {
        val value: ItemWithQuantity = itemsInCart[item.label] ?: ItemWithQuantity(item, 1)
        val newValue = value.copy(quantity = value.quantity - 1)
        if (newValue.quantity == 0L) {
            itemsInCart.remove(item.label)
        } else {
            itemsInCart[item.label] = newValue
        }
        sendUpdateChannel()
    }

    override fun itemsInCartChannel(): ReceiveChannel<List<ItemWithQuantity>> {
        return channel.openSubscription()
    }

    private suspend fun sendUpdateChannel() {
        channel.send(itemsInCart.values.toList().sortedBy { it.item.label })
    }
}
