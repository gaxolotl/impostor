package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.example.data.model.GameSettings
import com.example.data.model.Language
import com.example.data.model.Localization
import com.example.data.model.Player
import com.example.ui.components.FrostedBackground
import com.example.ui.components.GameHeader
import com.example.ui.components.SwipeRevealCard
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.SurfaceGlass

@Composable
fun RevealScreen(
    language: Language,
    players: List<Player>,
    currentIndex: Int,
    settings: GameSettings,
    categoryName: String,
    onToggleLanguage: (Language) -> Unit,
    onConfirmSeen: () -> Unit
) {
    val currentPlayer = players.getOrNull(currentIndex) ?: players.firstOrNull()

    FrostedBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            GameHeader(
                title = String.format("%d / %d", currentIndex + 1, players.size),
                language = language,
                onToggleLanguage = onToggleLanguage
            )

            if (currentPlayer != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Prompt Banner
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(1.dp, GlassBorder),
                        colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = Localization.getString("pass_phone_to", language),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = GoldYellow,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentPlayer.name,
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            )
                        }
                    }

                    // Interactive Card
                    SwipeRevealCard(
                        player = currentPlayer,
                        showHint = settings.showHintToImpostor,
                        categoryName = categoryName,
                        language = language,
                        onConfirmSeen = onConfirmSeen
                    )

                    // Bottom Hint Text
                    Text(
                        text = Localization.getString("release_to_hide", language),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.5f)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        }
    }
}
