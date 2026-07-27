package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.data.model.AppThemeColor
import com.example.data.model.ThemeMode

@Composable
fun ImpostorTheme(
    appThemeColor: AppThemeColor = AppThemeColor.VIOLET,
    themeMode: ThemeMode = ThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val primaryColor = Color(appThemeColor.primaryHex)
    val secondaryColor = Color(appThemeColor.secondaryHex)

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = primaryColor,
            onPrimary = Color.White,
            primaryContainer = primaryColor.copy(alpha = 0.3f),
            onPrimaryContainer = Color.White,
            secondary = secondaryColor,
            onSecondary = Color.White,
            secondaryContainer = secondaryColor.copy(alpha = 0.25f),
            onSecondaryContainer = Color.White,
            tertiary = MintGreen,
            onTertiary = Color.Black,
            background = DarkBackground,
            onBackground = TextPrimary,
            surface = SurfaceDark,
            onSurface = TextPrimary,
            surfaceVariant = SurfaceVariantDark,
            onSurfaceVariant = TextSecondary,
            error = ImpostorRed,
            onError = Color.White,
            outline = GlassBorder
        )
    } else {
        lightColorScheme(
            primary = primaryColor,
            onPrimary = Color.White,
            primaryContainer = primaryColor.copy(alpha = 0.15f),
            onPrimaryContainer = Color(0xFF1E1B28),
            secondary = secondaryColor,
            onSecondary = Color.White,
            secondaryContainer = secondaryColor.copy(alpha = 0.15f),
            onSecondaryContainer = Color(0xFF1E1B28),
            tertiary = MintGreen,
            onTertiary = Color.Black,
            background = Color(0xFFF8FAFC),
            onBackground = Color(0xFF0F172A),
            surface = Color.White,
            onSurface = Color(0xFF0F172A),
            surfaceVariant = Color(0xFFF1F5F9),
            onSurfaceVariant = Color(0xFF475569),
            error = ImpostorRed,
            onError = Color.White,
            outline = Color(0xFFCBD5E1)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

