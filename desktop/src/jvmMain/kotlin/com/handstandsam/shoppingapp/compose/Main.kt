package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.handstandsam.shoppingapp.cart.InMemoryShoppingCartDao
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.compose.DesktopShoppingAppImageLoader
import com.handstandsam.shoppingapp.compose.ShoppingCartScreen
import com.handstandsam.shoppingapp.compose.TextWithShadow
import com.handstandsam.shoppingapp.features.checkout.ShoppingCartViewModel
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ShoppingApp Desktop",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
            ),
        ) {
            MaterialTheme {
                val shoppingCart = ShoppingCart(InMemoryShoppingCartDao())
                val scope = CoroutineScope(Dispatchers.IO)
                val tetris = Item(
                    label = "Tetris",
                    image = "https://shopping-app.s3.amazonaws.com/video-games/images/nes-tetris.jpg"
                )
                val superMario3 = Item(
                    label = "Super Mario Bros. 3",
                    image = "https://shopping-app.s3.amazonaws.com/video-games/images/nes-mario3.png"
                )
                scope.launch {
                    shoppingCart.incrementItemInCart(tetris)
                    shoppingCart.incrementItemInCart(tetris)
                    shoppingCart.incrementItemInCart(superMario3)
                }
                val shoppingCartViewModel = ShoppingCartViewModel(scope, shoppingCart)
                Column {
                    // TextWithShadow("Hi")
                    ShoppingCartScreen(
                        itemsInCart = shoppingCart.itemsInCart,
                        homeUpClicked = {},
                        checkoutClicked = {},
                        logoutClicked = {},
                        shoppingCartViewModel = shoppingCartViewModel,
                        shoppingAppImageLoader = DesktopShoppingAppImageLoader()
                    )
                    // RootContent(
                    //     modifier = Modifier.fillMaxSize()
                    // )
                }
            }
        }
    }
}