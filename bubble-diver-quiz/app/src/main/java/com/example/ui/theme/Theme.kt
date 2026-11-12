package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BubbleDiverColorScheme = lightColorScheme(
    primary = OceanBlue,
    onPrimary = Color.White,
    primaryContainer = OceanBlueLight,
    onPrimaryContainer = OceanBlueDark,
    secondary = CoralOrange,
    onSecondary = Color.White,
    tertiary = SunnyYellow,
    onTertiary = OceanBlueDark,
    background = OceanBlueDark,
    onBackground = Color.White,
    surface = Color.White,
    onSurface = OceanBlueDark
)

@Composable
fun BubbleDiverQuizTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = BubbleDiverColorScheme,
        typography = Typography,
        content = content
    )
}
