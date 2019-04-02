package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.cart.sqldelight.Database
import com.handstandsam.shoppingapp.models.Item
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test


class ShoppingCartSqlDelightTest {

    private lateinit var testDelegate: TestDelegate

    @Before
    fun setUp() {
        testDelegate = TestDelegate()
    }

    @Test
    fun happyPath() = runBlocking<Unit> {
        testDelegate
            .assertTotalItemsInCart(0, 0)
            .addItem(item1)
            .assertPersisted(item1, 1)
            .assertTotalItemsInCart(1, 1)
            .addItem(item2)
            .assertPersisted(item1, quantity = 1)
            .assertPersisted(item2, quantity = 1)
            .assertTotalItemsInCart(2, 2)
            .addItem(item1)
            .assertPersisted(item1, quantity = 2)
            .assertTotalItemsInCart(2, 3)
            .removeItem(item2)
            .assertTotalItemsInCart(1, 2)
            .assertPersisted(item1, quantity = 2)
            .clearDb()
            .assertTotalItemsInCart(0, 0)
    }

    @Test
    fun `remove item that is not there`() = runBlocking<Unit> {
        testDelegate
            .assertTotalItemsInCart(0, 0)
            .removeItem(item2)
            .assertTotalItemsInCart(0, 0)
    }

    @Test
    fun `add 300 items`() = runBlocking<Unit> {
        val limit = 300
        (1..limit).forEach { _ ->
            testDelegate.addItem(item1)
        }

        testDelegate.assertTotalItemsInCart(1, limit)
    }


    inner class TestDelegate {
        private val sqlDriver: SqlDriver = JdbcSqliteDriver()

        init {
            Database.Schema.create(sqlDriver)
        }

        private val shoppingCart: ShoppingCart = ShoppingCartSqlDelight(
            sqlDriver = sqlDriver
        )

        suspend fun addItem(item: Item) = apply {
            shoppingCart.addItem(item)
            println("addItem finished: ${shoppingCart.itemsInCart().first()}")
        }

        suspend fun assertPersisted(item: Item, quantity: Int) = apply {
            println("asserting there is $quantity of $item")
            val matchingItemsInCart = shoppingCart.itemsInCart().filter { it.item == item }
            assertThat(matchingItemsInCart.size).isEqualTo(1)
            val matchingItemInCart = matchingItemsInCart[0]
            assertThat(matchingItemInCart.item).isEqualTo(item)
            assertThat(matchingItemInCart.quantity).isEqualTo(quantity)
        }

        suspend fun assertTotalItemsInCart(typeCount: Int, totalCount: Int) = apply {
            println("asserting there are $typeCount types of items with a total of $totalCount items")
            val itemsInCart = shoppingCart.itemsInCart()

            val itemTypeCount = itemsInCart.size
            assertThat(itemTypeCount).isEqualTo(typeCount)

            val totalItems = itemsInCart.sumBy { it.quantity }
            assertThat(totalItems).isEqualTo(totalCount)
        }

        suspend fun removeItem(item: Item) = apply {
            println("removeItem $item")
            shoppingCart.removeItem(item)
            println("removeItem finished: ${shoppingCart.itemsInCart()}")
        }

        fun clearDb() = apply {
            shoppingCart.empty()
            println("empty finished: ${shoppingCart.itemsInCart()}")
        }

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
