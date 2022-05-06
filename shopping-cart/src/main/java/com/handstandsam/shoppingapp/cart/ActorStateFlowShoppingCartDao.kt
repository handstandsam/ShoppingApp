package com.handstandsam.shoppingapp.cart

import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.MutableStateFlow

class ActorStateFlowShoppingCartDao : ShoppingCartDao {

    /** Emits Changes, and allows us to set values internally */
    override val allItems = MutableStateFlow<List<ItemWithQuantity>>(listOf())

    /** We could also call these Messages */
    sealed class Intention {
        data class FindByLabel(
            val label: String,
            val deferred: CompletableDeferred<ItemWithQuantity?>
        ) : Intention()

        data class Upsert(val itemWithQuantity: ItemWithQuantity) : Intention()
        data class Remove(val itemWithQuantity: ItemWithQuantity) : Intention()
        object Empty : Intention()
    }


    private val itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

    /**
     * Need to find a solution to replace the JVM Kotlin Actor we had.
     * This here may be susceptible to concurrency issues.
     * https://github.com/handstandsam/ShoppingApp/issues/51
     */
    private fun send(intention: Intention) {
        when (intention) {
            is Intention.FindByLabel -> {
                intention.deferred.complete(itemsInCart[intention.label])
            }
            is Intention.Upsert -> {
                itemsInCart[intention.itemWithQuantity.item.label] = intention.itemWithQuantity
            }
            is Intention.Remove -> {
                itemsInCart.remove(intention.itemWithQuantity.item.label)
            }
            is Intention.Empty -> {
                itemsInCart.clear()
            }
        }

        allItems.value = itemsInCart.asSortedList()
    }

    override suspend fun empty() {
        send(Intention.Empty)
    }

    override suspend fun upsert(itemWithQuantity: ItemWithQuantity) {
        send(Intention.Upsert(itemWithQuantity))
    }

    override suspend fun remove(itemWithQuantity: ItemWithQuantity) {
        send(Intention.Remove(itemWithQuantity))
    }

    override suspend fun findByLabel(label: String): ItemWithQuantity? {
        val deferred = CompletableDeferred<ItemWithQuantity?>()
        send(
            Intention.FindByLabel(
                label = label,
                deferred = deferred
            )
        )
        return deferred.await()
    }
}
