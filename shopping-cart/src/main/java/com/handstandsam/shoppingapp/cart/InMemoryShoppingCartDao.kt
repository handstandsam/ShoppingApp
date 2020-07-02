package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * In memory implementation of our [ShoppingCartDao]
 */
class InMemoryShoppingCartDao : ShoppingCartDao {

    private val itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

    private val channel = ConflatedBroadcastChannel(listOf<ItemWithQuantity>())

    override suspend fun findByLabel(label: String): ItemWithQuantity? {
        return itemsInCart[label]
    }

    override suspend fun upsert(itemWithQuantity: ItemWithQuantity) {
        itemsInCart[itemWithQuantity.item.label] = itemWithQuantity
        sendUpdateChannel()
    }

    override suspend fun remove(itemWithQuantity: ItemWithQuantity) {
        itemsInCart.remove(itemWithQuantity.item.label)
        sendUpdateChannel()
    }

    override suspend fun empty() {
        itemsInCart.clear()
        sendUpdateChannel()
    }

    override val allItems: Flow<List<ItemWithQuantity>>
        get() = channel.openSubscription()
            .consumeAsFlow()

    private suspend fun sendUpdateChannel() {
        channel.send(
            itemsInCart.values.toList()
                .sortedBy { it.item.label }
        )
    }

}
