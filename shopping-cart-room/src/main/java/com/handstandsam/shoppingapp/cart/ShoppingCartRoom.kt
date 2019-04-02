package com.handstandsam.shoppingapp.cart

import android.content.Context
import androidx.room.Room
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch


class ShoppingCartRoom(appContext: Context) : CoroutineScope by CoroutineScope(Dispatchers.IO),
    ShoppingCart {

    private val itemInCartDatabase: ItemInCartDatabase = Room.databaseBuilder(
        appContext,
        ItemInCartDatabase::class.java,
        "itemInCart_room"
    ).build()

    private val itemInCartDao = itemInCartDatabase.itemInCartDao()

    override suspend fun itemsInCart(): List<ItemWithQuantity> {
        return itemInCartDao.selectAll().map { it.toItemWithQuantity() }
    }

    private val channel = ConflatedBroadcastChannel(listOf<ItemWithQuantity>())

    override suspend fun empty() {
        itemInCartDao.empty()
        sendUpdateChannel()
    }

    override suspend fun addItem(item: Item) {
        val identityWithQuantity: ItemWithQuantity =
            itemInCartDao.findByLabel(item.label)?.toItemWithQuantity()
                ?: ItemWithQuantity(item, 0)

        val newValue = identityWithQuantity.copy(quantity = (identityWithQuantity.quantity + 1))
        itemInCartDao.upsert(newValue.toItemInCart())

        sendUpdateChannel()
    }

    override suspend fun removeItem(item: Item) {

        val itemWithQuantity: ItemWithQuantity? =
            itemInCartDao.findByLabel(item.label)?.toItemWithQuantity()

        if (itemWithQuantity != null) {
            val newValue = itemWithQuantity.copy(
                quantity = (itemWithQuantity.quantity - 1)
            )
            if (newValue.quantity <= 0L) {
                itemInCartDao.remove(newValue.toItemInCart())
            } else {
                itemInCartDao.upsert(newValue.toItemInCart())
            }
        }
        sendUpdateChannel()
    }

    override fun itemsInCartChannel(): ReceiveChannel<List<ItemWithQuantity>> {
        return channel.openSubscription()
    }

    private fun sendUpdateChannel() {
        launch {
            channel.send(itemsInCart())
        }
    }


}

private fun ItemWithQuantity.toItemInCart(): ItemInCart {
    return ItemInCart(
        image = this.item.image,
        label = this.item.label,
        link = this.item.link,
        quantity = this.quantity
    )
}

private fun ItemInCart.toItemWithQuantity(): ItemWithQuantity {
    return ItemWithQuantity(
        item = Item(
            image = this.image,
            label = this.label,
            link = this.link
        ),
        quantity = this.quantity
    )
}
