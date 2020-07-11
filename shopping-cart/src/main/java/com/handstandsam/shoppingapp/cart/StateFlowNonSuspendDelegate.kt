package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * [ShoppingCartDao] using [StateFlow] which doesn't require suspend or coroutines to access
 */
class StateFlowNonSuspendDelegate {

    // private mimic of a key to value db
    private val _itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

    // private mutable state flow, requires initial value
    private val itemsInCart = MutableStateFlow<List<ItemWithQuantity>>(listOf())

    // publicly exposed as read-only state flow
    val allItems: StateFlow<List<ItemWithQuantity>> get() = itemsInCart

    fun findByLabel(label: String): ItemWithQuantity? {
        return _itemsInCart[label]
    }

    fun upsert(itemWithQuantity: ItemWithQuantity) {
        _itemsInCart[itemWithQuantity.item.label] = itemWithQuantity
        itemsInCart.value = _itemsInCart.asSortedList()
    }

    fun remove(itemWithQuantity: ItemWithQuantity) {
        _itemsInCart.remove(itemWithQuantity.item.label)
        itemsInCart.value = _itemsInCart.asSortedList()
    }

    fun empty() {
        _itemsInCart.clear()
        itemsInCart.value = _itemsInCart.asSortedList()
    }

    private fun MutableMap<String, ItemWithQuantity>.asSortedList(): List<ItemWithQuantity> {
        return values.toList()
            .sortedBy { it.item.label }
    }

}