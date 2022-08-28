package com.handstandsam.shoppingapp.features.home

import com.handstandsam.shoppingapp.MviViewModel
import com.handstandsam.shoppingapp.cart.SessionManager
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.network.Response
import com.handstandsam.shoppingapp.repository.CategoryRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    scope: CoroutineScope,
    sessionManager: SessionManager,
    categoryRepo: CategoryRepo
) : MviViewModel<
        HomeViewModel.State,
        HomeViewModel.Intention,
        HomeViewModel.SideEffect>(
    scope = scope,
    initialState = State()
) {

    init {
        scope.launch {
            val categoriesResult = withContext(Dispatchers.Default) {
                categoryRepo.getCategories()
            }
            when (categoriesResult) {
                is Response.Success<List<Category>> -> {
                    send(Intention.CategoriesReceived(categoriesResult.body))
                }
                else -> {
                    throw RuntimeException("Category Response Error")
                }
            }
        }

        sessionManager.currentUser
            .onEach { currentUser ->
                println("onEach currentUser: $currentUser")
                if (currentUser != null) {
                    val welcomeStr =
                        "Welcome back " + currentUser.firstname + " " + currentUser.lastname
                    send(Intention.WelcomeMessage(welcomeStr))
                } else {
                    sessionManager.logout()
                    sideEffect(SideEffect.Logout)
                }
            }
            .launchIn(scope)
    }

    override fun reduce(state: State, intention: Intention): State {
        return when (intention) {
            is Intention.CategoryClicked -> {
                sideEffect(SideEffect.LaunchCategoryActivity(intention.category))
                state
            }
            is Intention.CategoriesReceived -> {
                state.copy(
                    categories = intention.categories
                )
            }
            is Intention.WelcomeMessage -> {
                state.copy(
                    welcomeMessage = intention.welcomeMessage
                )
            }
        }
    }

    data class State(
        val categories: List<Category> = listOf(),
        val welcomeMessage: String = ""
    )

    sealed interface Intention {
        data class WelcomeMessage(val welcomeMessage: String) : Intention
        data class CategoriesReceived(val categories: List<Category>) : Intention
        data class CategoryClicked(val category: Category) : Intention
    }

    sealed interface SideEffect {
        data class LaunchCategoryActivity(val category: Category) : SideEffect
        object Logout : SideEffect
    }
}
