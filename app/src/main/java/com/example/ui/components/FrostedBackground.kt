package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun FrostedBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val bgColor = MaterialTheme.colorScheme.background
    val primaryGlow = MaterialTheme.colorScheme.primary
    val secondaryGlow = MaterialTheme.colorScheme.secondary

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        // Ambient background glow Orbs based on Material 3 user accent color
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        primaryGlow.copy(alpha = 0.25f),
                        primaryGlow.copy(alpha = 0.08f),
                        Color.Transparent
                    )
                ),
                radius = size.width * 0.85f,
                center = androidx.compose.ui.geometry.Offset(x = size.width * 0.1f, y = size.height * 0.1f)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        secondaryGlow.copy(alpha = 0.20f),
                        secondaryGlow.copy(alpha = 0.05f),
                        Color.Transparent
                    )
                ),
                radius = size.width * 0.9f,
                center = androidx.compose.ui.geometry.Offset(x = size.width * 0.9f, y = size.height * 0.85f)
            )
        }

        content()
    }
}

