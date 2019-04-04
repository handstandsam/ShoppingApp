package com.handstandsam.shoppingapp.cart

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.handstandsam.shoppingapp.models.Item
import org.junit.Assert.assertEquals

class RoomShoppingCartTestDelegate {

    /**
     * InMemory Instance of the [Room] Database
     */
    private val shoppingCart: ShoppingCart = ShoppingCart(
        RoomShoppingCartDao(
            Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext,
                RoomItemInCartDatabase::class.java
            ).build()
        )
    )

    suspend fun incrementItemInCart(item: Item) = apply {
        println("adding item: $item")
        shoppingCart.incrementItemInCart(item)
        println("after adding item: ${shoppingCart.itemsInCart()}")
    }

    suspend fun assertPersisted(item: Item, quantity: Long) = apply {
        println("asserting there is $quantity of $item")
        val matchingItemsInCart = shoppingCart.itemsInCart()
            .filter { it.item.label == item.label }
        assertEquals(matchingItemsInCart.size, 1)
        val matchingItemInCart = matchingItemsInCart[0]
        assertEquals(matchingItemInCart.item, item)
        assertEquals(matchingItemInCart.quantity, quantity)
    }

    suspend fun assertTotalItemsInCart(typeCount: Int, totalCount: Int) = apply {
        println("asserting there are $typeCount types of items with a total of $totalCount items")
        val itemsInCart = shoppingCart.itemsInCart()

        val itemTypeCount = itemsInCart.size
        assertEquals(itemTypeCount, typeCount)

        val totalItems = itemsInCart.sumBy { it.quantity.toInt() }
        assertEquals(totalItems, totalCount)
    }

    suspend fun decrementItemInCart(item: Item) = apply {
        println("decrementItemInCart $item")
        shoppingCart.decrementItemInCart(item)
        println("decrementItemInCart finished: ${shoppingCart.itemsInCart()}")
    }

    suspend fun clearDb() = apply {
        shoppingCart.empty()
        println("empty finished: ${shoppingCart.itemsInCart()}")
    }

}