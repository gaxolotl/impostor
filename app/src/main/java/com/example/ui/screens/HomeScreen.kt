package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.GamePhase
import com.example.data.model.Language
import com.example.data.model.Localization
import com.example.ui.components.FrostedBackground
import com.example.ui.components.GameHeader
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.MintGreen
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryNeonViolet
import com.example.ui.theme.SecondaryNeonPink
import com.example.ui.theme.SurfaceGlass

@Composable
fun HomeScreen(
    language: Language,
    onToggleLanguage: (Language) -> Unit,
    onNavigate: (GamePhase) -> Unit
) {
    FrostedBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            GameHeader(
                title = Localization.getString("app_title", language),
                language = language,
                onToggleLanguage = onToggleLanguage
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Hero Banner Card with Frosted Border
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, GlassBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_impostor_hero_1785182272238),
                            contentDescription = "Game Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, DarkBackground.copy(alpha = 0.95f))
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(20.dp)
                        ) {
                            Text(
                                text = Localization.getString("app_title", language),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = Localization.getString("app_subtitle", language),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Main Play CTA Button (Gradient Purple to Indigo)
                Button(
                    onClick = { onNavigate(GamePhase.SETUP) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .testTag("play_now_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryNeonViolet
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MintGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = Localization.getString("play_now", language),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Cards: Custom Categories & Leaderboard
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Custom Categories Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(130.dp)
                            .clickable { onNavigate(GamePhase.CATEGORIES) }
                            .testTag("custom_categories_card"),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(1.dp, GlassBorder),
                        colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SecondaryNeonPink.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Category,
                                    contentDescription = null,
                                    tint = SecondaryNeonPink
                                )
                            }
                            Text(
                                text = Localization.getString("custom_categories", language),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }

                    // Leaderboard Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(130.dp)
                            .clickable { onNavigate(GamePhase.LEADERBOARD) }
                            .testTag("leaderboard_card"),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(1.dp, GlassBorder),
                        colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(GoldYellow.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Leaderboard,
                                    contentDescription = null,
                                    tint = GoldYellow
                                )
                            }
                            Text(
                                text = Localization.getString("leaderboard", language),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Game Instructions Quick Summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, GlassBorder),
                    colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = "🎮 " + if (language == Language.EN) "How to Play" else "Как се играе",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = GoldYellow
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (language == Language.EN)
                                "1. Pass phone around & swipe up to see secret word or Impostor role.\n2. Discuss, ask sneaky questions & find out who doesn't know the word!\n3. Vote for the Impostor before time runs out!"
                            else
                                "1. Предавайте телефона и плъзгайте нагоре за тайната дума.\n2. Задавайте въпроси и открийте кой не знае думата!\n3. Гласувайте за импостъра преди времето да изтече!",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.85f),
                                lineHeight = 22.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
