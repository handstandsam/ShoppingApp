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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * A SqlDelight Implementation of our [ShoppingCartDao] to read and write to the Database
 */
class SqlDelightShoppingCartDao(sqlDriver: SqlDriver) :
    CoroutineScope by CoroutineScope(Dispatchers.IO),
    ShoppingCartDao {

    /**
     * Exposed as a [ReceiveChannel] for consumers to listen to updates to the Database contents
     */
    private val channel = ConflatedBroadcastChannel<List<ItemWithQuantity>>(listOf())

    /**
     * The SqlDelight queries that were created during code generation from our .sq file
     */
    private val itemInCartEntityQueries = Database(sqlDriver).itemInCartEntityQueries

    override suspend fun selectAll(): List<ItemWithQuantity> {
        return itemInCartEntityQueries.selectAll()
            .executeAsList()
            .toItemWithQuantityList()
    }

    override val selectAllStream: Flow<List<ItemWithQuantity>>
        get() = channel.openSubscription()
            .consumeAsFlow()

    override suspend fun findByLabel(label: String): ItemWithQuantity? {
        return itemInCartEntityQueries.selectByLabel(label)
            .executeAsOneOrNull()
            ?.toItemWithQuantity()
    }

    override suspend fun upsert(itemWithQuantity: ItemWithQuantity) {
        itemInCartEntityQueries.insertOrReplace(
            label = itemWithQuantity.item.label,
            image = itemWithQuantity.item.image,
            link = itemWithQuantity.item.link,
            quantity = itemWithQuantity.quantity
        )
    }

    override suspend fun remove(itemWithQuantity: ItemWithQuantity) {
        itemInCartEntityQueries.deleteByLabel(itemWithQuantity.item.label)
    }

    override suspend fun empty() {
        itemInCartEntityQueries.empty()
    }

    /**
     * Publishes the current [List] of [ItemWithQuantity] to the [ReceiveChannel]
     */
    private fun sendUpdateToChannel() {
        launch {
            channel.send(selectAll())
        }
    }

    /**
     * Listener that will get fired if the db is updated, so we can notify the [ReceiveChannel]
     */
    private val changeListener = object : Query.Listener {
        override fun queryResultsChanged() {
            sendUpdateToChannel()
        }
    }

    /**
     * We need to register our listener for updates, and publish our latest contents to the channel on initialization
     */
    init {
        itemInCartEntityQueries.selectAll().addListener(changeListener) //Listens for updates
        sendUpdateToChannel() //This is required so that we put the current contents on the channel
    }

}

/**
 * Extension Function converting the SqlDelight Model into our App's Model
 */
private fun ItemInCart.toItemWithQuantity(): ItemWithQuantity {
    return ItemWithQuantity(
        item = Item(
            label = label,
            image = image,
            link = link
        ),
        quantity = quantity
    )
}

/**
 * Extension Function mapping the SqlDelight Model into our App's Model
 */
private fun List<ItemInCart>.toItemWithQuantityList(): List<ItemWithQuantity> {
    return this.map {
        it.toItemWithQuantity()
    }
}
