package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow

/**
 * In memory implementation of our [ShoppingCartDao]
 */
class InMemoryShoppingCartDao : ShoppingCartDao {

    private val itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

    private val itemWithQuantity = MutableStateFlow(listOf<ItemWithQuantity>())

    override suspend fun findByLabel(label: String): ItemWithQuantity? {
        return itemsInCart[label]
    }

    override suspend fun upsert(itemWithQuantity: ItemWithQuantity) {
        itemsInCart[itemWithQuantity.item.label] = itemWithQuantity
        this.itemWithQuantity.value = itemsInCart.asSortedList()
    }

    override suspend fun remove(itemWithQuantity: ItemWithQuantity) {
        itemsInCart.remove(itemWithQuantity.item.label)
        this.itemWithQuantity.value = itemsInCart.asSortedList()
    }

    override suspend fun empty() {
        itemsInCart.clear()
        itemWithQuantity.value = itemsInCart.asSortedList()
    }

    override val allItems: Flow<List<ItemWithQuantity>>
        get() = itemWithQuantity

}
