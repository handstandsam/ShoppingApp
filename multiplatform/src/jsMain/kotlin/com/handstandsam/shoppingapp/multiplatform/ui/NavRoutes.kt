package com.handstandsam.shoppingapp.multiplatform.ui

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import kotlinx.coroutines.flow.MutableStateFlow

val navController: MutableStateFlow<NavRoutes> = MutableStateFlow(NavRoutes.HomeScreen)

sealed interface NavRoutes {
    object HomeScreen : NavRoutes
    data class CategoryDetailScreen(val category: Category) : NavRoutes
    data class ItemDetailScreen(val item: Item) : NavRoutes
}