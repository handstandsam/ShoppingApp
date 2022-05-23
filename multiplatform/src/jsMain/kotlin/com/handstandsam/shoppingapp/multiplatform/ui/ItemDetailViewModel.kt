package com.handstandsam.shoppingapp.multiplatform.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

class ItemDetailViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined),
    initialState: State = State(),
    private val shoppingCart: ShoppingCart,
) : JsMviViewModel<ItemDetailViewModel.State, ItemDetailViewModel.Intent>(
    scope,
    initialState
) {
    sealed interface Intent {
        data class InitialState(val item: Item) : Intent
        data class AddItemToCart(val item: Item) : Intent
    }

    data class State(
        val item: Item? = null,
    )

    override fun reduce(state: State, intent: Intent): State {
        return when (intent) {
            is Intent.InitialState -> {
                state.copy(
                    item = intent.item
                )
            }
            is Intent.AddItemToCart -> {
                scope.launch {
                    shoppingCart.incrementItemInCart(intent.item)
                }
                state.copy()
            }
        }
    }

    @Composable
    fun ItemDetailScreen(item: Item) {
        sendIntention(Intent.InitialState(item))
        val state: State by states.collectAsState()
        Div {
            WrappedPreformattedText(state.toString())
            ImageAndTextRow(label = item.label, imageUrl = item.image) {
                sendIntention(Intent.AddItemToCart(item))
            }
            Button(attrs = {
                onClick {
                    sendIntention(Intent.AddItemToCart(item))
                }
            }) { Text("Add to Cart") }
        }
    }
}