package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GameColorScheme = darkColorScheme(
    primary = PrimaryNeonViolet,
    onPrimary = Color.White,
    primaryContainer = PrimaryGlow,
    onPrimaryContainer = Color.White,
    secondary = SecondaryNeonPink,
    onSecondary = Color.White,
    tertiary = MintGreen,
    onTertiary = Color.Black,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondary,
    error = ImpostorRed,
    onError = Color.White
)

@Composable
fun ImpostorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GameColorScheme,
        typography = Typography,
        content = content
    )
}
