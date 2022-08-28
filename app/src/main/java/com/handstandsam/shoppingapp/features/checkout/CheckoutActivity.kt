package com.handstandsam.shoppingapp.features.checkout

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.compose.AndroidShoppingAppImageLoader
import com.handstandsam.shoppingapp.compose.ShoppingCartScreen
import com.handstandsam.shoppingapp.features.home.AndroidShoppingCartViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CheckoutActivity : LoggedInActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val shoppingCartViewModel = ViewModelProvider(this, graph.viewModelFactory)
            .get(AndroidShoppingCartViewModel::class.java).viewModel

        lifecycleScope.launchWhenCreated {
            shoppingCartViewModel.sideEffects
                .onEach {
                    when (it) {
                        is ShoppingCartViewModel.SideEffect.LaunchCheckout -> {
                            val toast = Toast.makeText(
                                applicationContext,
                                "TODO: Checkout Feature Not Implemented",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    }
                }
                .launchIn(this)
        }

        setContent {

            ShoppingCartScreen(
                itemsInCart = graph
                    .sessionGraph
                    .shoppingCart
                    .itemsInCart,
                shoppingCartViewModel = shoppingCartViewModel,
                checkoutClicked = {
                    shoppingCartViewModel.send(
                        ShoppingCartViewModel.Intention.CheckoutClicked()
                    )
                },
                logoutClicked = { logout() },
                homeUpClicked = { onBackPressed() },
                shoppingAppImageLoader = AndroidShoppingAppImageLoader()
            )
        }
    }
}
