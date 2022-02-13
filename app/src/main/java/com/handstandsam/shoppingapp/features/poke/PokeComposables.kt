package com.handstandsam.shoppingapp.features.poke

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

/**
 * Draggable/Scaling: https://developer.android.com/jetpack/compose/gestures
 * Animating: https://stackoverflow.com/questions/67080502/animating-circle-drawing-in-jetpack-compose
 * DP to PX: https://stackoverflow.com/questions/65921799/how-to-convert-dp-to-pixels-in-android-jetpack-compose
 */
@Preview
@Composable
fun Pokeball(
    modifier: Modifier = Modifier,
    sizedp: Dp = 100.dp
) {
    val sizepx = with(LocalDensity.current) { sizedp.toPx() }
    Box(
        modifier = Modifier
            .size(sizedp)
    ) {
        val blackLineColor = Color.Black
        val strokeWidth = sizepx * .04f
        val outterBallPercentage = .25.toFloat()
        val innerBallPercentage = .17.toFloat()
        val innerestBallPercentage = .10.toFloat()
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val darkWhite = Color(0xFFCCCCCC)
                drawArc(
                    brush = Brush.radialGradient(listOf(Color.White, darkWhite)),
                    startAngle = 0f,
                    sweepAngle = 180f, // * animateFloat.value,
                    useCenter = false
                )
                val darkRed = Color(0xFFAA0000)
                drawArc(
                    brush = Brush.radialGradient(listOf(Color.Red, darkRed)),
                    startAngle = 180f,
                    sweepAngle = 180f, // * animateFloat.value,
                    useCenter = false
                )
                drawArc(
                    color = blackLineColor,
                    startAngle = 0f,
                    sweepAngle = 360f, // * animateFloat.value,
                    useCenter = false,
                    style = Stroke(strokeWidth)
                )

                drawLine(
                    color = blackLineColor,
                    start = Offset(
                        x = 0f,
                        y = (size.height / 2)
                    ),
                    end = Offset(
                        x = size.width,
                        y = (size.height / 2)
                    ),
                    strokeWidth = strokeWidth

                )
                val outterBallSizePx = sizepx * outterBallPercentage
                drawCircle(
                    color = Color.Black,
                    radius = outterBallSizePx / 2,
                )

                val innerBallSizePx = sizepx * innerBallPercentage
                drawCircle(
                    color = Color.White,
                    radius = innerBallSizePx / 2,
                )

                val innerestBallSizePx = sizepx * innerestBallPercentage
                val innerestBallMarginPx = ((sizepx - innerestBallSizePx) / 2)
                drawArc(
                    color = Color.LightGray,
                    startAngle = 0f,
                    sweepAngle = 360f, // * animateFloat.value,
                    useCenter = false,
                    topLeft = Offset(
                        x = innerestBallMarginPx,
                        y = innerestBallMarginPx,
                    ),
                    size = Size(
                        width = innerestBallSizePx,
                        height = innerestBallSizePx,
                    ),
                    style = Stroke(4f)
                )

            }
        }
    }
}

@Composable
fun CurrentPokemonInfo(
    modifier: Modifier = Modifier,
    pokemonName: String,
    pokemonImageUrl: String,
    pokemonAnimatedImageScale: Float,
    onRestart: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .height(100.dp)
        )
        Text(
            text = pokemonName,
            style = MaterialTheme.typography.h4.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(4f, 4f),
                    blurRadius = 8f
                )
            ),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    onRestart()
                }
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(150.dp))
        Image(
            painter = rememberImagePainter(pokemonImageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
                .scale(pokemonAnimatedImageScale)
        )
    }
}