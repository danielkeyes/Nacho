package dev.danielkeyes.nacho.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = NachoBlue,
    primaryVariant = NachoBlueDark,
    secondary = NachoRed,
    secondaryVariant = NachoRedDark,
    background = Color.Black,
    surface = Color.Black,
    error = Color(0xFFB00020),
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White
)

private val DarkColorPalette = darkColors(
)

@Composable
fun NachoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}