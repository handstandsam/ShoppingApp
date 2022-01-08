package com.handstandsam.shoppingapp.features.countby

import com.handstandsam.shoppingapp.MviViewModel
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.NetworkResult
import com.handstandsam.shoppingapp.utils.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountByViewModel(
    private val scope: CoroutineScope
) : MviViewModel<
        CountByViewModel.State,
        CountByViewModel.Intention,
        CountByViewModel.SideEffect>(
    scope = scope,
    initialState = State()
) {

    override fun reduce(state: State, intention: Intention): State {
        return when (intention) {
            is Intention.ItemClicked -> {
                sideEffect(SideEffect.LaunchItemDetailActivity(intention.item))
                state
            }
            is Intention.CategoriesReceived -> {
                state.copy(
                    items = intention.items
                )
            }
            is Intention.CategoryLabelSet -> {
                state.copy(
                    categoryLabel = intention.categoryLabel
                )
            }
        }.exhaustive
    }

    data class State(
        val items: List<Item> = listOf(),
        val categoryLabel: String? = null
    )

    sealed class Intention {
        data class CategoriesReceived(val items: List<Item>) : Intention()
        data class ItemClicked(val item: Item) : Intention()
        data class CategoryLabelSet(val categoryLabel: String) : Intention()
    }

    sealed class SideEffect {
        data class LaunchItemDetailActivity(val item: Item) : SideEffect()

    }
}
