package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GamePhase
import com.example.data.model.Language
import com.example.data.model.Localization
import com.example.data.model.Player
import com.example.data.model.RoundResult
import com.example.ui.components.FrostedBackground
import com.example.ui.components.GameHeader
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.ImpostorRed
import com.example.ui.theme.MintGreen
import com.example.ui.theme.PrimaryNeonViolet
import com.example.ui.theme.SecondaryNeonPink
import com.example.ui.theme.SurfaceGlass

@Composable
fun ResultScreen(
    language: Language,
    result: RoundResult?,
    players: List<Player>,
    onToggleLanguage: (Language) -> Unit,
    onPlayNextRound: () -> Unit,
    onMainMenu: () -> Unit
) {
    FrostedBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            GameHeader(
                title = Localization.getString("round_results", language),
                language = language,
                onToggleLanguage = onToggleLanguage
            )

            if (result != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Victory Banner Card
                    val isCaught = result.isImpostorCaught
                    val bannerBg = if (isCaught) MintGreen.copy(alpha = 0.2f) else ImpostorRed.copy(alpha = 0.2f)
                    val bannerTitle = if (isCaught)
                        Localization.getString("impostor_caught", language)
                    else
                        Localization.getString("impostor_escaped", language)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(1.5.dp, if (isCaught) MintGreen else ImpostorRed),
                        colors = CardDefaults.cardColors(containerColor = bannerBg)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (isCaught) "🕵️‍♂️" else "🎭",
                                fontSize = 48.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = bannerTitle,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (isCaught) MintGreen else ImpostorRed,
                                    letterSpacing = 1.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Game Details Reveal Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(1.dp, GlassBorder),
                        colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            // Real Secret Word
                            Text(
                                text = Localization.getString("the_secret_word_was", language),
                                style = MaterialTheme.typography.labelLarge.copy(color = GoldYellow, fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = result.secretWord,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Real Impostor Name(s)
                            Text(
                                text = Localization.getString("the_impostors_were", language),
                                style = MaterialTheme.typography.labelLarge.copy(color = ImpostorRed, fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = result.impostorNames.joinToString(", "),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = ImpostorRed
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Points Breakdown Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(1.dp, GlassBorder),
                        colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "🏆 " + Localization.getString("points_summary", language),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GoldYellow
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            players.forEach { player ->
                                val pts = result.pointsAwarded[player.id] ?: 0
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = player.name,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = GoldYellow,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "+$pts Pts",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = MintGreen
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Action Buttons
                    Button(
                        onClick = onPlayNextRound,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("play_next_round_button"),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryNeonViolet)
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = Localization.getString("play_again", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onMainMenu,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("main_menu_button"),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, GlassBorder)
                    ) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = Localization.getString("back_to_menu", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}
