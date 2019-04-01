package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

class CheckoutCart : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

    private val channel = ConflatedBroadcastChannel(itemsInCart.values.toList())

    fun empty() {
        itemsInCart.clear()
        sendUpdateChannel()
    }

    fun addItem(item: Item) {
        val value: ItemWithQuantity = itemsInCart[item.label] ?: ItemWithQuantity(item, 1)
        itemsInCart[item.label] = value.copy(quantity = value.quantity + 1)
        sendUpdateChannel()
    }

    fun removeItem(item: Item) {
        val value: ItemWithQuantity = itemsInCart[item.label] ?: ItemWithQuantity(item, 1)
        val newValue = value.copy(quantity = value.quantity - 1)
        if (newValue.quantity == 0) {
            itemsInCart.remove(item.label)
        } else {
            itemsInCart[item.label] = newValue
        }
        sendUpdateChannel()
    }

    fun itemsInCartStream(): ReceiveChannel<List<ItemWithQuantity>> {
        return channel.openSubscription()
    }

    val items: List<ItemWithQuantity>
        get() = itemsInCart.values.toList()


    private fun sendUpdateChannel() {
        launch {
            channel.send(items)
        }
    }
}
