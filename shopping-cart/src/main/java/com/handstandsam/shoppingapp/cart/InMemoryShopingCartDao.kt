package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel


class InMemoryShopingCartDao : ShoppingCartDao {

    private val itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

    private val channel = ConflatedBroadcastChannel(listOf<ItemWithQuantity>())

    override suspend fun selectAll(): List<ItemWithQuantity> {
        return itemsInCart.values.toList().sortedBy { it.item.label }
    }

    override suspend fun findByLabel(label: String): ItemWithQuantity? {
        return itemsInCart[label]
    }

    override suspend fun upsert(itemInCart: ItemWithQuantity) {
        itemsInCart[itemInCart.item.label] = itemInCart
        sendUpdateChannel()
    }

    override suspend fun remove(itemInCart: ItemWithQuantity) {
        itemsInCart.remove(itemInCart.item.label)
        sendUpdateChannel()
    }

    override suspend fun empty() {
        itemsInCart.clear()
        sendUpdateChannel()
    }

    override suspend fun selectAllStream(): ReceiveChannel<List<ItemWithQuantity>> {
        return channel.openSubscription()
    }

    private suspend fun sendUpdateChannel() {
        channel.send(selectAll())
    }

}
