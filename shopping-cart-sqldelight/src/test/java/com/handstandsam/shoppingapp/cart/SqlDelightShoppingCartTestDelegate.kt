package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.cart.sqldelight.Database
import com.handstandsam.shoppingapp.models.Item
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.assertj.core.api.Assertions.assertThat

/**
 * Our Test Delegate that abstracts away assertions in our unit tests.
 * Kind of like the Robot pattern.
 */
class SqlDelightShoppingCartTestDelegate {


    /**
     * Our Database Driver that runs on the JVM.
     */
    val sqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        Database.Schema.create(this)
    }


    private val shoppingCart: ShoppingCart = ShoppingCart(
        SqlDelightShoppingCartDao(sqlDriver)
    )

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

        val totalItems = itemsInCart.sumBy { it.quantity.toInt() }
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