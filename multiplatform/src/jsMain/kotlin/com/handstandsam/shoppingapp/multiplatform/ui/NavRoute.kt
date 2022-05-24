package com.handstandsam.shoppingapp.multiplatform.ui

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.MutableStateFlow

val navController: MutableStateFlow<NavRoute> = MutableStateFlow(NavRoute.HomeScreen)

sealed interface NavRoute {
    object HomeScreen : NavRoute
    data class CategoryDetailScreen(val category: Category) : NavRoute
    data class ItemDetailScreen(val item: Item) : NavRoute
    data class CartScreen(val itemsWithQuantity: List<ItemWithQuantity>) : NavRoute
}