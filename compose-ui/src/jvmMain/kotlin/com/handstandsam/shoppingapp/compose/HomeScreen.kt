package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.handstandsam.shoppingapp.features.home.HomeViewModel
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.flow.Flow


@Composable
fun HomeScreen(
    itemsInCart: Flow<List<ItemWithQuantity>>,
    showCartClicked: () -> Unit,
    logoutClicked: () -> Unit,
    homeViewModel: HomeViewModel,
    shoppingAppImageLoader: ShoppingAppImageLoader,
) {
    AppScaffold(
        itemsInCart = itemsInCart,
        showCartClicked = showCartClicked,
        logoutClicked = logoutClicked
    ) {
        val state by homeViewModel.states.collectAsState(initial = HomeViewModel.State())
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = state.welcomeMessage,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                state.categories.forEach { category ->
                    item {
                        CategoryRow(category, shoppingAppImageLoader ) {
                            homeViewModel.send(HomeViewModel.Intention.CategoryClicked(category))
                        }
                    }
                }
            }
        }
    }
}