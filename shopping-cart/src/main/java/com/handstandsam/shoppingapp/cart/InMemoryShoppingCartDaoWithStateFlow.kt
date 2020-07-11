package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow



/**
 * In memory implementation of our [ShoppingCartDao] using [StateFlow]
 */
class InMemoryShoppingCartDaoWithStateFlow() : ShoppingCartDao {

    /** Delegate that shows we don't need suspend with [StateFlow] */
    private val delegate = StateFlowNonSuspendDelegate()

    override val allItems: Flow<List<ItemWithQuantity>> = delegate.allItems

    override suspend fun findByLabel(label: String): ItemWithQuantity? {
        return delegate.findByLabel(label)
    }

    override suspend fun upsert(itemWithQuantity: ItemWithQuantity) {
        delegate.upsert(itemWithQuantity)
    }

    override suspend fun remove(itemWithQuantity: ItemWithQuantity) {
        delegate.remove(itemWithQuantity)
    }

    override suspend fun empty() {
        delegate.empty()
    }

}
