package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.handstandsam.shoppingapp.models.Category

@Composable
fun CategoryRow(category: Category, shoppingAppImageLoader: ShoppingAppImageLoader, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        shoppingAppImageLoader.loadImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { onClick() },
            image = category.image,
        )
        TextWithShadow(
            text = category.label,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}