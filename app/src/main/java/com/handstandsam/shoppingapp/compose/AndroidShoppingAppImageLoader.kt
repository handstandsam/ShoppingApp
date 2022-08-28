package com.handstandsam.shoppingapp.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.coil.CoilImage

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