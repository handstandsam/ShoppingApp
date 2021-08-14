package com.handstandsam.shoppingapp.compose

import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.Category
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CategoryView(category: Category, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { onClick() },
            imageModel = category.image,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows an image with a circular revealed animation.
            circularRevealedEnabled = true
        )
//        MyAndroidTextView(
//            text = category.label,
//            modifier = Modifier
//                .fillMaxSize()
//                .align(Alignment.Center)
//                .padding(16.dp)
//        )
        TextWithShadow(
            text = category.label,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(16.dp),
            style = MaterialTheme.typography.h4.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }

}

@Composable
fun MyAndroidTextView(text: String, modifier: Modifier) {
    // Adds view to Compose
    AndroidView(
        modifier = modifier, // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates custom view
            AppCompatTextView(context).apply {
                setTextAppearance(R.style.ItemRowTitle)
                this.text = text
            }
        }
    )
}

/**
 * Look into later as alternate solution: https://stackoverflow.com/a/66958833/509081
 */
@Composable
fun TextWithShadow(
    text: String,
    style: TextStyle,
    color: Color = Color.White,
    shadowColor: Color = Color.DarkGray,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = shadowColor,
        modifier = modifier
            .offset(
                x = 2.dp,
                y = 2.dp
            )
            .alpha(0.75f),
        style = style
    )
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = style
    )
}