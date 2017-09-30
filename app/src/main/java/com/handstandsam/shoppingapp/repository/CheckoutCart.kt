package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item

data class CheckoutCart(val itemsInCart: MutableList<Item> = mutableListOf()) {

    fun empty() {
        itemsInCart.clear()
    }

    fun addItem(item: Item) {
        itemsInCart.add(item)
    }

    fun removeItem(item: Item) {
        itemsInCart.remove(item)
    }

    val items: List<Item>
        get() = itemsInCart
}
