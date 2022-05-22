package com.handstandsam.shoppingapp.multiplatform.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.multiplatform.JsMultiplatformApi
import com.handstandsam.shoppingapp.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.maxHeight
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text

class CategoryDetailViewModel {

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

    val intents = MutableStateFlow<Intent>(Intent.NoState)

    val states = MutableStateFlow(State())

    fun reduce(state: State, intent: Intent): State {
        return when (intent) {
            Intent.NoState -> {
                State()
            }
            is Intent.ItemClicked -> {
                navController.tryEmit(NavRoutes.ItemDetailScreen(intent.item))
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
                            intents.emit(Intent.ItemsForCategoryReceived(result.body))
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
        Div(attrs = {}) {
            categories.forEach { item ->
                ImageAndTextRow(label = item.label, imageUrl = item.image) {
                    intents.tryEmit(Intent.ItemClicked(item))
                }
            }
        }
    }

    @Composable
    fun CategoryDetailScreen(category: Category) {
        intents.tryEmit(Intent.InitialState(category))

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

    init {
        CoroutineScope(Dispatchers.Default).launch {
            intents.collect { event ->
                val state = states.value
                states.value = reduce(state, event)
            }
        }
    }
}