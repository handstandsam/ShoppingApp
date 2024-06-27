package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.handstandsam.shoppingapp.R

private val appFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.roboto_black,
            weight = FontWeight.W900,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_black_italic,
            weight = FontWeight.W900,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_bold,
            weight = FontWeight.W700,
            style = FontStyle.Normal
        )
    )
)

private val defaultTypography = Typography()
val appTypography = Typography(

displayLarge = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    displayMedium = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    displaySmall = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    headlineLarge = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    headlineMedium = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    headlineSmall = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    titleLarge = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    titleMedium = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    titleSmall = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    bodyLarge = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    bodyMedium = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    bodySmall = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    labelLarge = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    labelMedium = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    labelSmall = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
)


private val dark = darkColorScheme(
//    primary = purple200,
//    primaryVariant = purple700,
//    secondary = teal200
)

private val light = lightColorScheme(
//    background = Color.White,
//    secondary = teal200,
//    background = Color.LightGray

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

val shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
fun MyApplicationTheme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isSystemInDarkTheme) dark else light

    MaterialTheme(
        colorScheme = colors,
        typography = appTypography,
        shapes = shapes,
        content = content
    )
}