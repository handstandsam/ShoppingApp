package com.handstandsam.shoppingapp.cart

import android.util.Log
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow

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

    private val scope = CoroutineScope(Dispatchers.Default)

    private val actor = scope.actor<Intention> {

        val itemsInCart: MutableMap<String, ItemWithQuantity> = mutableMapOf()

        for (intention in channel) {
            Log.d("Actor Intention", intention.toString())
            when (intention) {
                is Intention.FindByLabel -> {
                    intention.deferred
                        .complete(itemsInCart[intention.label])
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

            val newValues = itemsInCart.asSortedList()
            Log.d("New Values", newValues.toString())

            if (allItems.value != newValues) {
                allItems.value = newValues
            }
        }
    }


    override suspend fun empty() {
        actor.send(Intention.Empty)
    }

    override suspend fun upsert(itemWithQuantity: ItemWithQuantity) {
        actor.send(Intention.Upsert(itemWithQuantity))
    }

    override suspend fun remove(itemWithQuantity: ItemWithQuantity) {
        actor.send(Intention.Remove(itemWithQuantity))
    }

    override suspend fun findByLabel(label: String): ItemWithQuantity? {
        val deferred = CompletableDeferred<ItemWithQuantity?>()
        actor.send(
            Intention.FindByLabel(
                label = label,
                deferred = deferred
            )
        )
        return deferred.await()
    }
}
