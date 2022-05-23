package com.handstandsam.shoppingapp.multiplatform.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

class CartViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined),
    initialState: State = State(),
    private val shoppingCart: ShoppingCart,
) : JsMviViewModel<CartViewModel.State, CartViewModel.Intent>(
    scope,
    initialState
) {

    sealed interface Intent {
        data class ItemsWithQuantityUpdated(val items: List<ItemWithQuantity>) : Intent
        data class IncrementClicked(val item: Item) : Intent
        data class DecrementClicked(val item: Item) : Intent
    }

    data class State(
        val items: List<ItemWithQuantity> = listOf(),
    )

    override fun reduce(state: State, intent: Intent): State {
        return when (intent) {
            is Intent.DecrementClicked -> {
                scope.launch {
                    shoppingCart.decrementItemInCart(intent.item)
                }
                state
            }
            is Intent.IncrementClicked -> {
                scope.launch {
                    shoppingCart.incrementItemInCart(intent.item)
                }
                state
            }
            is Intent.ItemsWithQuantityUpdated -> {
                state.copy(
                    items = intent.items
                )
            }
        }
    }

    @Composable
    fun CartScreen(itemsWithQuantity: List<ItemWithQuantity>) {
        val state: State by states.collectAsState()
        Div {
            WrappedPreformattedText("CartScreen state: ${state.toString()}")

            if (state.items.isEmpty()) {
                H1 { Text("No Items in Cart") }
            } else {
                state.items.forEach {
                    val item = it.item
                    ImageAndTextRow(
                        label = item.label + " (${it.quantity})",
                        imageUrl = item.image
                    )
                    Button(
                        attrs = {
                            onClick {
                                sendIntention(Intent.IncrementClicked(item))
                            }
                        }
                    ) {
                        Text("Increment")
                    }
                    Button(
                        attrs = {
                            onClick {
                                sendIntention(Intent.DecrementClicked(item))
                            }
                        }
                    ) {
                        Text("Decrement")
                    }
                }
            }
        }
    }

    init {
        scope.launch {
            shoppingCart.itemsInCart.collect {
                sendIntention(Intent.ItemsWithQuantityUpdated(it))
            }
        }
    }

}