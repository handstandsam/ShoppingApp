package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.Item
import org.assertj.core.api.Assertions.assertThat

class TestDelegate {

    private val shoppingCart: ShoppingCart = ShoppingCart(InMemoryShoppingCartDao())

    suspend fun incrementItemInCart(item: Item) = apply {
        println("adding item: $item")
        shoppingCart.incrementItemInCart(item)
        println("after adding item: ${shoppingCart.latestItemsInCart()}")
    }

    suspend fun assertPersisted(item: Item, quantity: Long) = apply {
        println("asserting there is $quantity of $item")
        val matchingItemsInCart = shoppingCart.latestItemsInCart()
            .filter { it.item.label == item.label }
        assertThat(matchingItemsInCart.size).isEqualTo(1)
        val matchingItemInCart = matchingItemsInCart[0]
        assertThat(matchingItemInCart.item).isEqualTo(item)
        assertThat(matchingItemInCart.quantity).isEqualTo(quantity)
    }

    suspend fun assertTotalItemsInCart(typeCount: Int, totalCount: Int) = apply {
        println("asserting there are $typeCount types of items with a total of $totalCount items")
        val itemsInCart = shoppingCart.latestItemsInCart()

        val itemTypeCount = itemsInCart.size
        assertThat(itemTypeCount).isEqualTo(typeCount)

        val totalItems = itemsInCart.sumOf { it.quantity.toInt() }
        assertThat(totalItems).isEqualTo(totalCount)
    }

    suspend fun decrementItemInCart(item: Item) = apply {
        println("decrementItemInCart $item")
        shoppingCart.decrementItemInCart(item)
        println("decrementItemInCart finished: ${shoppingCart.latestItemsInCart()}")
    }

    suspend fun clearDb() = apply {
        shoppingCart.empty()
        println("empty finished: ${shoppingCart.latestItemsInCart()}")
    }

}