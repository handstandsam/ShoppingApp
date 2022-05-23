package com.handstandsam.shoppingapp.multiplatform.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.multiplatform.JsMultiplatformApi
import com.handstandsam.shoppingapp.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

class CategoryDetailViewModel(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined),
    initialState: State = State()
) : JsMviViewModel<CategoryDetailViewModel.State, CategoryDetailViewModel.Intent>(
    scope,
    initialState
) {

    sealed interface Intent {
        object NoState : Intent
        data class InitialState(val category: Category) : Intent
        data class ItemsForCategoryReceived(val items: List<Item>) : Intent
        data class ItemClicked(val item: Item) : Intent
    }

    data class State(
        val category: Category? = null,
        val isLoading: Boolean = true,
        val items: List<Item> = listOf(),
    )

    override fun reduce(state: State, intent: Intent): State {
        return when (intent) {
            Intent.NoState -> {
                State()
            }
            is Intent.ItemClicked -> {
                navController.tryEmit(NavRoute.ItemDetailScreen(intent.item))
                state.copy()
            }
            is Intent.ItemsForCategoryReceived -> {
                state.copy(
                    isLoading = false,
                    items = intent.items
                )
            }
            is Intent.InitialState -> {
                CoroutineScope(Dispatchers.Default).launch {
                    val result =
                        JsMultiplatformApi().networkGraph.itemRepo.getItemsForCategory(intent.category.label)
                    when (result) {
                        is Response.Success -> {
                            sendIntention(Intent.ItemsForCategoryReceived(result.body))
                        }
                        is Response.Failure -> TODO()
                    }
                }
                state.copy(
                    isLoading = true
                )
            }
        }
    }

    @Composable
    fun ItemsComposable(categories: List<Item>) {
        ShoppingAppList {
            categories.forEach { item ->
                ImageAndTextRow(label = item.label, imageUrl = item.image) {
                    sendIntention(Intent.ItemClicked(item))
                }
            }
        }
    }

    @Composable
    fun CategoryDetailScreen(category: Category) {
        sendIntention(Intent.InitialState(category))

        val state: State by states.collectAsState()
        Div {
            WrappedPreformattedText(state.toString())
            if (state.isLoading) {
                H1 { Text("LOADING Items for Category ${state.category}...") }
            } else {
                ItemsComposable(state.items)
            }
        }
    }
}