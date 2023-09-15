package com.handstandsam.shoppingapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.features.category.CategoryViewModel
import com.handstandsam.shoppingapp.features.checkout.ShoppingCartViewModel
import com.handstandsam.shoppingapp.repository.AndroidSessionManager
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import kotlinx.coroutines.CoroutineScope

class AndroidHomeViewModel(val viewModel: HomeViewModel) : ViewModel()
class AndroidCategoryViewModel(val viewModel: CategoryViewModel) : ViewModel()
class AndroidShoppingCartViewModel(val viewModel: ShoppingCartViewModel) : ViewModel()

class ShoppingAppViewModelFactory(
    private val scope: CoroutineScope,
    private val sessionManager: AndroidSessionManager,
    private val categoryRepo: CategoryRepo,
    private val shoppingCart: ShoppingCart,
    private val itemRepo: ItemRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AndroidHomeViewModel::class.java)) {
            AndroidHomeViewModel(
                HomeViewModel(
                    scope = scope,
                    sessionManager = sessionManager,
                    categoryRepo = categoryRepo
                )
            ) as T
        } else if (modelClass.isAssignableFrom(AndroidCategoryViewModel::class.java)) {
            AndroidCategoryViewModel(
                CategoryViewModel(
                    scope = scope,
                    itemRepo = itemRepo
                )
            ) as T
        } else if (modelClass.isAssignableFrom(AndroidShoppingCartViewModel::class.java)) {
            AndroidShoppingCartViewModel(
                ShoppingCartViewModel(
                    scope = scope,
                    cart = shoppingCart
                )
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}