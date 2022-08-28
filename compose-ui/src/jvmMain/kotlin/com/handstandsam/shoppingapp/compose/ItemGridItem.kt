package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import com.handstandsam.shoppingapp.models.Item

@Composable
fun ItemGridItem(item: Item, shoppingAppImageLoader: ShoppingAppImageLoader, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        shoppingAppImageLoader.loadImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
                .alpha(0.9f)
                .clickable { onClick() },
            image = item.image,
        )
        // CoilImage(
        //     // Crop, Fit, Inside, FillHeight, FillWidth, None
        //     contentScale = ContentScale.Crop,
        //     // shows an image with a circular revealed animation.
        //     circularRevealedEnabled = true
        // )
        TextWithShadow(
            text = item.label,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}