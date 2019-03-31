package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

data class CheckoutCart(val itemsInCart: MutableList<Item> = mutableListOf()) :
    CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val channel = ConflatedBroadcastChannel<List<Item>>(itemsInCart.toList())

    fun empty() {
        itemsInCart.clear()
        sendUpdateChannel()
    }

    fun addItem(item: Item) {
        itemsInCart.add(item)
        sendUpdateChannel()
    }

    fun removeItem(item: Item) {
        itemsInCart.remove(item)
        sendUpdateChannel()
    }

    fun itemsInCartStream(): ReceiveChannel<List<Item>> {
        return channel.openSubscription()
    }

    val items: List<Item>
        get() = itemsInCart.toList()


    private fun sendUpdateChannel() {
        launch {
            channel.send(itemsInCart.toList())
        }
    }
}
