package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.handstandsam.shoppingapp.features.category.CategoryViewModel
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.Flow

@Composable
fun CategoryScreen(
    itemsInCart: Flow<List<ItemWithQuantity>>,
    showCartClicked: () -> Unit,
    logoutClicked: () -> Unit,
    homeUpClicked: () -> Unit,
    itemClicked: (item: Item) -> Unit,
    categoryViewModel: CategoryViewModel,
    shoppingAppImageLoader: ShoppingAppImageLoader,
) {
    val state by categoryViewModel.states.collectAsState(initial = CategoryViewModel.State())
    AppScaffold(
        itemsInCart = itemsInCart,
        showCartClicked = showCartClicked,
        logoutClicked = logoutClicked,
        homeUpClicked = homeUpClicked,
        title = state.categoryLabel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                GridCells.Fixed(2)
            ) {
                items(state.items.size) {
                    val item = state.items[it]
                    ItemGridItem(item, shoppingAppImageLoader) {
                        itemClicked(item)
                    }
                }
            }
        }
    }
}