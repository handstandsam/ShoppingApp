package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch



class ShoppingCartInMemory : CoroutineScope by CoroutineScope(Dispatchers.Default),
    ShoppingCart {

    private val itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

    private val channel = ConflatedBroadcastChannel(itemsInCart.values.toList())

    override fun empty() {
        itemsInCart.clear()
        sendUpdateChannel()
    }

    override fun addItem(item: Item) {
        val value: ItemWithQuantity = itemsInCart[item.label] ?: ItemWithQuantity(item, 0)
        itemsInCart[item.label] = value.copy(quantity = value.quantity + 1)
        sendUpdateChannel()
    }

    override fun removeItem(item: Item) {
        val value: ItemWithQuantity = itemsInCart[item.label] ?: ItemWithQuantity(item, 1)
        val newValue = value.copy(quantity = value.quantity - 1)
        if (newValue.quantity == 0) {
            itemsInCart.remove(item.label)
        } else {
            itemsInCart[item.label] = newValue
        }
        sendUpdateChannel()
    }

    override fun itemsInCart(): ReceiveChannel<List<ItemWithQuantity>> {
        return channel.openSubscription()
    }

    private fun sendUpdateChannel() {
        launch {
            channel.send(itemsInCart.values.toList())
        }
    }
}
