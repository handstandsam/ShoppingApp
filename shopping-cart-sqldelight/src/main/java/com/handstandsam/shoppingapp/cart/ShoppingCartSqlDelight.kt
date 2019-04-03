package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.cart.sqldelight.Database
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

class ShoppingCartSqlDelight(sqlDriver: SqlDriver) :
    CoroutineScope by CoroutineScope(Dispatchers.IO),
    ShoppingCart {

    private val itemInCartEntityQueries = Database(sqlDriver).itemInCartEntityQueries

    override suspend fun empty() {
        itemInCartEntityQueries.empty()
    }

    override suspend fun addItem(item: Item) {
        val itemInCart = itemInCartEntityQueries
            .selectByLabel(item.label)
            .executeAsOneOrNull()

        val quantity = itemInCart?.quantity ?: 0
        itemInCartEntityQueries.insertOrReplace(
            label = item.label,
            image = item.image,
            link = item.link,
            quantity = (quantity + 1)
        )
    }

    override suspend fun removeItem(item: Item) {
        val itemInCart = itemInCartEntityQueries
            .selectByLabel(item.label)
            .executeAsOneOrNull()

        itemInCart?.let {
            val newOneLessQuantity: Long = (itemInCart.quantity - 1)

            if (newOneLessQuantity == 0L) {
                itemInCartEntityQueries.deleteByLabel(item.label)
            } else {
                itemInCartEntityQueries.insertOrReplace(
                    label = itemInCart.label,
                    link = itemInCart.link,
                    image = itemInCart.image,
                    quantity = newOneLessQuantity
                )
            }
        }

    }

    private fun selectAll(): List<ItemWithQuantity> {
        return itemInCartEntityQueries.selectAll().executeAsList().map {
            ItemWithQuantity(
                item = Item(
                    label = it.label,
                    image = it.image,
                    link = it.link
                ),
                quantity = it.quantity
            )
        }
    }

    private val channel = ConflatedBroadcastChannel(selectAll())

    override fun itemsInCartChannel(): ReceiveChannel<List<ItemWithQuantity>> {
        return channel.openSubscription()
    }

    private val changeListener = object : Query.Listener {
        override fun queryResultsChanged() {
            launch {
                channel.send(selectAll())
            }
        }
    }

    init {
        itemInCartEntityQueries.selectAll().addListener(changeListener)
    }

}
