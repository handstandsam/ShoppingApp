package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextWithShadow(
    text: String,
    color: Color = Color.White,
    shadowColor: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = shadowColor,
                offset = Offset(4f, 4f),
                blurRadius = 8f
            )
        ),
        textAlign = textAlign
    )
}