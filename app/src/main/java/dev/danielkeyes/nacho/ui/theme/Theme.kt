package dev.danielkeyes.nacho.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = NachoBlue,
    primaryVariant = NachoBlueLight,
    secondary = NachoRed,
    secondaryVariant = NachoRedLight
//    primary = NachoRed,
//    primaryVariant = NachoRedLight,
//    secondary = NachoBlue,
//    secondaryVariant = NachoBlueLight
)

private val LightColorPalette = lightColors(
    primary = NachoBlue,
    primaryVariant = NachoBlueDark,
    secondary = NachoRed,
    secondaryVariant = NachoRedDark,
//    primary = NachoRed,
//    primaryVariant = NachoRedDark,
//    secondary = NachoBlue,
//    secondaryVariant = NachoBlueDark

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun NachoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}