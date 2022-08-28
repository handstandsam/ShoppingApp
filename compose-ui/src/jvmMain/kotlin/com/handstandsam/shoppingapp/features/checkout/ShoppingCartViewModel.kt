package com.handstandsam.shoppingapp.features.checkout

import com.handstandsam.shoppingapp.MviViewModel
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.handstandsam.shoppingapp.models.totalItemCount

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShoppingCartViewModel(
    private val scope: CoroutineScope,
    private val cart: ShoppingCart
) : MviViewModel<
        ShoppingCartViewModel.State,
        ShoppingCartViewModel.Intention,
        ShoppingCartViewModel.SideEffect>(
    scope = scope,
    initialState = State()
) {

    init {
        scope.launch {
            cart.itemsInCart
                .collect { itemsInCart: List<ItemWithQuantity> ->
                    send(Intention.CartUpdated(itemsInCart))
                }
        }
    }

    override fun reduce(state: State, intention: Intention): State {
        return when (intention) {
            is Intention.CheckoutClicked -> {
                sideEffect(SideEffect.LaunchCheckout())
                state
            }
            is Intention.CartUpdated -> {
                val cartStatusLabel = intention.items.totalItemCount().toString() + " item(s) in your cart."
                state.copy(
                    items = intention.items,
                    cartStatusLabel = cartStatusLabel
                )
            }
            is Intention.DecrementClicked -> {
                scope.launch {
                    cart.decrementItemInCart(intention.itemWithQuantity.item)
                }
                state
            }
            is Intention.IncrementClicked -> {
                scope.launch {
                    cart.incrementItemInCart(intention.itemWithQuantity.item)
                }
                state
            }
        }
    }

    data class State(
        val items: List<ItemWithQuantity> = listOf(),
        val cartStatusLabel: String = ""
    )

    sealed class Intention {
        data class CartUpdated(val items: List<ItemWithQuantity>) : Intention()
        class CheckoutClicked() : Intention()
        data class IncrementClicked(val itemWithQuantity: ItemWithQuantity) : Intention()
        data class DecrementClicked(val itemWithQuantity: ItemWithQuantity) : Intention()
    }

    sealed class SideEffect {
        class LaunchCheckout() : SideEffect()
    }
}
