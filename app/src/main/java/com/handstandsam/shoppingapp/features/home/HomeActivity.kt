package com.handstandsam.shoppingapp.features.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.compose.HomeScreen
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import com.handstandsam.shoppingapp.utils.exhaustive
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeActivity : LoggedInActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val homeViewModel = ViewModelProvider(this, graph.viewModelFactory)
            .get(HomeViewModel::class.java)

        lifecycleScope.launchWhenCreated {
            homeViewModel.sideEffects
                .onEach {
                    when (it) {
                        is HomeViewModel.SideEffect.LaunchCategoryActivity -> {
                            CategoryActivity.launch(this@HomeActivity, it.category)
                        }
                    }.exhaustive
                }
                .launchIn(this)
        }

        setContent {
            HomeScreen(
                itemsInCart = graph
                    .sessionGraph
                    .shoppingCart
                    .itemsInCart,
                homeViewModel = homeViewModel,
                showCartClicked = { startCheckoutActivity() },
                logoutClicked = { logout() }
            )
        }
    }
}
