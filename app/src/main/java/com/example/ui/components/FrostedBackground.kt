package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.DarkBackground

@Composable
fun FrostedBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Ambient background glow Orbs
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top-Left Purple Ambient Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x409333EA), // Purple-600
                        Color(0x15581C87),
                        Color.Transparent
                    )
                ),
                radius = size.width * 0.85f,
                center = androidx.compose.ui.geometry.Offset(x = size.width * 0.1f, y = size.height * 0.1f)
            )

            // Bottom-Right Indigo Ambient Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x356366F1), // Indigo-500
                        Color(0x10312E81),
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
