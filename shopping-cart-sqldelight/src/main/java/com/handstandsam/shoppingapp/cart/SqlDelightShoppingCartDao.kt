package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.cart.sqldelight.Database
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * A SqlDelight Implementation of our [ShoppingCartDao] to read and write to the Database
 */
class SqlDelightShoppingCartDao(sqlDriver: SqlDriver) :
    CoroutineScope by CoroutineScope(Dispatchers.IO),
    ShoppingCartDao {

    /**
     * The SqlDelight queries that were created during code generation from our .sq file
     */
    private val itemInCartEntityQueries = Database(sqlDriver).itemInCartEntityQueries

    override val allItems: Flow<List<ItemWithQuantity>>
        get() = itemInCartEntityQueries.selectAll()
            .asFlow()
            .map { query ->
                query.executeAsList()
                    .toItemWithQuantityList()
            }


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
