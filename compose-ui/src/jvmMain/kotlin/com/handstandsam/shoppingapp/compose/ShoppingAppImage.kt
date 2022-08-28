package com.handstandsam.shoppingapp.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface ShoppingAppImageLoader {
    @Composable
    fun loadImage(modifier: Modifier, image: String)
}
