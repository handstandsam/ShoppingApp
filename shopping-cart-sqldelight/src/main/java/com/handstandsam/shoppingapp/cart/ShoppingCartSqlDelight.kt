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

    override fun empty() {
        itemInCartEntityQueries.empty()
    }

    override fun addItem(item: Item) {
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

    override fun removeItem(item: Item) {
        itemInCartEntityQueries.deleteByLabel(
            label = item.label
        )
    }

    private fun selectAll(): List<ItemWithQuantity> {
        return itemInCartEntityQueries.selectAll().executeAsList().map {
            ItemWithQuantity(
                item = Item(
                    label = it.label,
                    image = it.image,
                    link = it.link
                ),
                quantity = it.quantity.toInt()
            )
        }
    }

    private val channel = ConflatedBroadcastChannel(selectAll())

    override fun itemsInCartChannel(): ReceiveChannel<List<ItemWithQuantity>> {
        return channel.openSubscription()
    }


    override fun itemsInCart(): List<ItemWithQuantity> {
        return selectAll()
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
