package com.handstandsam.shoppingapp.multiplatform.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.multiplatform.JsMultiplatformApi
import com.handstandsam.shoppingapp.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

class HomeViewModel {

    sealed interface Intent {
        object PageOpened : Intent
        data class CategoriesReceived(val categories: List<Category>) : Intent
        data class CategoryClicked(val category: Category) : Intent
    }

    data class State(
        val isLoading: Boolean = true,
        val categories: List<Category> = listOf()
    )

    private val events = MutableStateFlow<Intent>(Intent.PageOpened)

    private val states = MutableStateFlow(State())

    fun reduce(state: State, intent: Intent): State {
        println("HomeReduce: $intent")
        return when (intent) {
            is Intent.CategoryClicked -> {
                navController.tryEmit(NavRoutes.CategoryDetailScreen(intent.category))
                state.copy()
            }
            is Intent.CategoriesReceived -> {
                state.copy(
                    isLoading = false,
                    categories = intent.categories
                )
            }
            Intent.PageOpened -> {
                CoroutineScope(Dispatchers.Default).launch {
                    val result = JsMultiplatformApi().networkGraph.categoryRepo.getCategories()
                    when (result) {
                        is Response.Success -> {
                            events.emit(Intent.CategoriesReceived(result.body))
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
    fun CategoriesComposable(categories: List<Category>) {
        Div(attrs = {}) {
            categories.forEach { category ->
                ImageAndTextRow(label = category.label, imageUrl = category.image, onClick = {
                    events.tryEmit(Intent.CategoryClicked(category))
                })
            }
        }
    }

    @Composable
    fun HomeScreen() {
        val state: State by states.collectAsState()
        Div {
            WrappedPreformattedText(state.toString())
            if (state.isLoading) {
                H1 { Text("LOADING...") }
            } else {
                CategoriesComposable(state.categories)
            }
        }
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            events.collect { event ->
                val state = states.value
                states.value = reduce(state, event)
            }
        }
    }
}