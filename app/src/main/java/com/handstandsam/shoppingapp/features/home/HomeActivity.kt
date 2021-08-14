package com.handstandsam.shoppingapp.features.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.compose.CategoryView
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeActivity : LoggedInActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val homeViewModel = ViewModelProvider(this, graph.homeViewModelFactory)
            .get(HomeViewModel::class.java)

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Shopping App")
                        },
                        actions = {
                            IconButton(onClick = {
                                startCheckoutActivity()
                            }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "")
                            }
                            var showMenu by remember { mutableStateOf(false) }
                            IconButton(onClick = { showMenu = !showMenu }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "")
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(onClick = { logout() }) {
                                    Text("Log out")
                                }
                            }
                        },
                    )
                }) {
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
                        style = MaterialTheme.typography.body1
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        state.categories.forEach { category ->
                            item {
                                CategoryView(category) {
                                    homeViewModel.send(HomeViewModel.Intention.CategoryClicked(category))
                                }
                            }
                        }
                    }
                }

            }

        }

        homeViewModel.sideEffects.onEach {
            when (it) {
                is HomeViewModel.SideEffect.LaunchCategoryActivity -> {
                    CategoryActivity.launch(this@HomeActivity, it.category)
                }
            }
        }
            .launchIn(lifecycleScope)

    }
}
