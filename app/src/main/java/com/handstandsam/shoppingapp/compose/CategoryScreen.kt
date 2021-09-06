package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.handstandsam.shoppingapp.features.category.CategoryViewModel
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryScreen(
    itemsInCart: Flow<List<ItemWithQuantity>>,
    checkoutClicked: () -> Unit,
    logoutClicked: () -> Unit,
    homeUpClicked: () -> Unit,
    categoryViewModel: CategoryViewModel
) {
    AppScaffold(
        itemsInCart = itemsInCart,
        checkoutClicked = checkoutClicked,
        logoutClicked = logoutClicked,
        homeUpClicked = homeUpClicked
    ) {
        val state by categoryViewModel.states.collectAsState(initial = CategoryViewModel.State())
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2)
            ) {
                items(state.items.size) {
                    val item = state.items[it]
                    ItemGridItem(item) {
                        categoryViewModel.send(CategoryViewModel.Intention.ItemClicked(item))
                    }
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(text = " $item")
//                    }
                }
            }
        }
    }
}