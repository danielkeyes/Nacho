package dev.danielkeyes.nacho.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SingleColorPalette = lightColors(
    primary = NachoTightsBlue,
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

private val LightColorPalette = lightColors(

)

private val DarkColorPalette = darkColors(
)

// Set to never use
@Composable
fun SoundBoardTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () ->
Unit) {
    // Not using dark or light, single color palette
    val colors = if (true) {
        SingleColorPalette
    } else if (darkTheme) {
        LightColorPalette
    } else {
        DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}