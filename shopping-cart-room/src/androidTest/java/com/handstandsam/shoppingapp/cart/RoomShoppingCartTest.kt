package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.Item
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RoomShoppingCartTest {

    private lateinit var testDelegate: RoomShoppingCartTestDelegate

    @Before
    fun setUp() {
        testDelegate = RoomShoppingCartTestDelegate()
    }

    @Test
    fun happyPath() = runBlocking<Unit> {
        testDelegate
            .assertTotalItemsInCart(0, 0)
            .incrementItemInCart(item1)
            .assertPersisted(item1, 1)
            .assertTotalItemsInCart(1, 1)
            .incrementItemInCart(item2)
            .assertPersisted(item1, quantity = 1)
            .assertPersisted(item2, quantity = 1)
            .assertTotalItemsInCart(2, 2)
            .incrementItemInCart(item1)
            .assertPersisted(item1, quantity = 2)
            .assertTotalItemsInCart(2, 3)
            .decrementItemInCart(item2)
            .assertTotalItemsInCart(1, 2)
            .assertPersisted(item1, quantity = 2)
            .clearDb()
            .assertTotalItemsInCart(0, 0)
    }

    @Test
    fun removeItemThatIsNotThere() = runBlocking<Unit> {
        testDelegate
            .assertTotalItemsInCart(0, 0)
            .decrementItemInCart(item2)
            .assertTotalItemsInCart(0, 0)
    }

    @Test
    fun add300Items() = runBlocking<Unit> {
        val limit = 300
        (1..limit).forEach { _ ->
            testDelegate.incrementItemInCart(item1)
        }
        testDelegate.assertTotalItemsInCart(1, limit)
    }

    companion object {
        val item1 = Item(
            label = "Cool Thing 1",
            image = "https://...jpg",
            link = null
        )

        val item2 = Item(
            label = "Cool Thing 2",
            image = "https://...jpg",
            link = null
        )
    }

}