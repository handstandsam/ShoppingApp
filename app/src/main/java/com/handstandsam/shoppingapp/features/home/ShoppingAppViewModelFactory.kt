package com.handstandsam.shoppingapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.handstandsam.shoppingapp.MviViewModel
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.features.category.CategoryViewModel
import com.handstandsam.shoppingapp.features.checkout.ShoppingCartViewModel
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.SessionManager
import kotlinx.coroutines.CoroutineScope

class AndroidHomeViewModel(val viewModel: HomeViewModel) : ViewModel()
class AndroidCategoryViewModel(val viewModel: CategoryViewModel) : ViewModel()
class AndroidShoppingCartViewModel(val viewModel: ShoppingCartViewModel) : ViewModel()

class ShoppingAppViewModelFactory(
    private val scope: CoroutineScope,
    private val sessionManager: SessionManager,
    private val categoryRepo: CategoryRepo,
    private val shoppingCart: ShoppingCart,
    private val itemRepo: ItemRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AndroidHomeViewModel::class.java)) {
            return AndroidHomeViewModel(
                HomeViewModel(
                    scope = scope,
                    sessionManager = sessionManager,
                    categoryRepo = categoryRepo
                )
            ) as T
        }
        else if (modelClass.isAssignableFrom(AndroidCategoryViewModel::class.java)) {
            return AndroidCategoryViewModel(
                CategoryViewModel(
                    scope = scope,
                    itemRepo = itemRepo
                )
            ) as T
        }
        else if (modelClass.isAssignableFrom(AndroidShoppingCartViewModel::class.java)) {
            return AndroidShoppingCartViewModel(
                ShoppingCartViewModel(
                    scope = scope,
                    cart = shoppingCart
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}