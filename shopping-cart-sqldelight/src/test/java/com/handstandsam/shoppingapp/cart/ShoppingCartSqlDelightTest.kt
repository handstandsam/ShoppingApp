package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.cart.sqldelight.Database
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
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
    fun testDbWrapper() = runBlocking<Unit> {
        testDelegate
            .assertNumberOfDbEntries(0)
            .addOrUpdate(itemWithQuantity1)
            .assertPersisted(itemWithQuantity1)
            .assertNumberOfDbEntries(1)
            .addOrUpdate(itemWithQuantity2)
            .assertPersisted(itemWithQuantity2)
            .assertNumberOfDbEntries(2)
            .removeItem(itemWithQuantity1)
            .assertNumberOfDbEntries(1)
            .removeItem(
                itemWithQuantity1.copy(
                    item = itemWithQuantity1.item.copy(label = "some other label")
                )
            )
            .assertNumberOfDbEntries(1)
            .clearDb()
            .assertNumberOfDbEntries(0)
    }

    inner class TestDelegate {
        private val sqlDriver: SqlDriver = JdbcSqliteDriver()

        init {
            Database.Schema.create(sqlDriver)
        }

        private val shoppingCart: ShoppingCart = ShoppingCartSqlDelight(
            sqlDriver = sqlDriver
        )

        suspend fun addOrUpdate(item: ItemWithQuantity) = apply {
            shoppingCart.addItem(item.item)
            println("addOrUpdate finished: ${shoppingCart.itemsInCart().first()}")
        }

        suspend fun assertPersisted(itemWithQuantity: ItemWithQuantity) = apply {
            val matches = shoppingCart.itemsInCart().filter { it == itemWithQuantity }
            assertThat(matches).isEmpty()
        }

        suspend fun assertNumberOfDbEntries(count: Int) = apply {
            val itemsInCart = shoppingCart.itemsInCart()
            println("itemsInCart: $itemsInCart")
            val actualCount = itemsInCart.size
            assertThat(actualCount).isEqualTo(count)
        }

        suspend fun removeItem(itemWithQuantity: ItemWithQuantity) = apply {
            shoppingCart.removeItem(itemWithQuantity.item)
            println("removeItem finished: ${shoppingCart.itemsInCart()}")
        }

        fun clearDb() = apply {
            shoppingCart.empty()
        }

    }

    companion object {
        val itemWithQuantity1 = ItemWithQuantity(
            Item(
                label = "Cool Thing",
                image = "https://...jpg",
                link = null
            ),
            5
        )
        val itemWithQuantity2 = ItemWithQuantity(
            Item(
                label = "Cool Thing 2",
                image = "https://...jpg",
                link = null
            ),
            2
        )
    }

}
