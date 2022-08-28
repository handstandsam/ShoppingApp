package com.handstandsam.shoppingapp.features.checkout

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.compose.ShoppingAppImageLoader
import com.handstandsam.shoppingapp.compose.ShoppingCartScreen
import com.handstandsam.shoppingapp.compose.loadNetworkImage
import com.handstandsam.shoppingapp.features.home.AndroidShoppingCartViewModel
import com.skydoves.landscapist.coil.CoilImage

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class AndroidShoppingAppImageLoader : ShoppingAppImageLoader {
    @Composable
    override fun loadImage(modifier: Modifier, image: String) {
        return CoilImage(
            modifier = modifier,
            imageModel = image,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows an image with a circular revealed animation.
            circularRevealedEnabled = true
        )
    }
}

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
                homeUpClicked = { onBackPressed() }
            )
        }
    }
}
