package com.handstandsam.shoppingapp.cart

import androidx.room.Room
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * Implements the app's [ShoppingCartDao] interface, but makes queries to our [Room] database [RoomItemInCartDatabase]
 */
class RoomShoppingCartDao(itemInCartDatabase: RoomItemInCartDatabase) :
    CoroutineScope by CoroutineScope(Dispatchers.IO),
    ShoppingCartDao {

    private val itemInCartDao: RoomItemInCartDao = itemInCartDatabase.itemInCartDao()

    private val channel = ConflatedBroadcastChannel(listOf<ItemWithQuantity>())

    init {
        /**
         * Initialize a Listener/Observer to the Room Database to publish updates to the [ReceiveChannel]
         */
        itemInCartDao.selectAllStream().observeForever { list ->
            launch {
                channel.send(list.toItemWithQuantityList())
            }
        }
    }

    override suspend fun selectAll(): List<ItemWithQuantity> {
        return itemInCartDao.selectAll().toItemWithQuantityList()
    }

    override val selectAllStream: Flow<List<ItemWithQuantity>>
        get() = channel.openSubscription()
            .consumeAsFlow()

    override suspend fun findByLabel(label: String): ItemWithQuantity? {
        return itemInCartDao.findByLabel(label)?.toItemWithQuantity()
    }

    override suspend fun upsert(itemWithQuantity: ItemWithQuantity) {
        itemInCartDao.upsert(itemWithQuantity.toItemInCart())
    }

    override suspend fun remove(itemWithQuantity: ItemWithQuantity) {
        itemInCartDao.remove(itemWithQuantity.toItemInCart())
    }

    override suspend fun empty() {
        itemInCartDao.empty()
    }

}

/**
 * Extension Function to convert [ItemWithQuantity] -> [RoomItemInCartEntity]
 */
private fun ItemWithQuantity.toItemInCart(): RoomItemInCartEntity {
    return RoomItemInCartEntity(
        image = this.item.image,
        label = this.item.label,
        link = this.item.link,
        quantity = this.quantity
    )
}

/**
 * Extension Function to convert [RoomItemInCartEntity] -> [ItemWithQuantity]
 */
private fun RoomItemInCartEntity.toItemWithQuantity(): ItemWithQuantity {
    return ItemWithQuantity(
        item = Item(
            image = this.image,
            label = this.label,
            link = this.link
        ),
        quantity = this.quantity
    )
}

/**
 * Extension Function to convert a [List] of [RoomItemInCartEntity] -> [List] of [ItemWithQuantity]
 */
private fun List<RoomItemInCartEntity>.toItemWithQuantityList(): List<ItemWithQuantity> {
    return map { it.toItemWithQuantity() }
}