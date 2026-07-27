package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.model.Language
import com.example.data.model.Localization
import com.example.ui.components.FrostedBackground
import com.example.ui.components.GameHeader
import com.example.ui.theme.CivilianBlue
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.ImpostorRed
import com.example.ui.theme.MintGreen
import com.example.ui.theme.PrimaryNeonViolet
import com.example.ui.theme.SecondaryNeonPink
import com.example.ui.theme.SurfaceGlass

@Composable
fun DiscussionScreen(
    language: Language,
    categoryName: String,
    secondsLeft: Int,
    isTimerRunning: Boolean,
    hasTimer: Boolean,
    onToggleLanguage: (Language) -> Unit,
    onTogglePause: () -> Unit,
    onVoteNow: () -> Unit
) {
    FrostedBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            GameHeader(
                title = Localization.getString("discussion_phase", language),
                language = language,
                onToggleLanguage = onToggleLanguage
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Category Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PrimaryNeonViolet.copy(alpha = 0.2f))
                        .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = String.format(Localization.getString("category_badge", language), categoryName),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = PrimaryNeonViolet,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // Big Timer Display
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape)
                            .background(
                                if (hasTimer && secondsLeft <= 10) ImpostorRed.copy(alpha = 0.2f) else SurfaceGlass
                            )
                            .border(1.dp, GlassBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = "Timer",
                                tint = if (hasTimer && secondsLeft <= 10) ImpostorRed else GoldYellow,
                                modifier = Modifier.size(36.dp)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            if (hasTimer) {
                                val mins = secondsLeft / 60
                                val secs = secondsLeft % 60
                                Text(
                                    text = String.format("%02d:%02d", mins, secs),
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 42.sp,
                                        color = if (secondsLeft <= 10) ImpostorRed else Color.White
                                    )
                                )
                            } else {
                                Text(
                                    text = "∞",
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 54.sp,
                                        color = MintGreen
                                    )
                                )
                            }
                        }
                    }

                    if (hasTimer) {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = onTogglePause,
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, GlassBorder),
                            modifier = Modifier.testTag("pause_resume_button")
                        ) {
                            Icon(
                                imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isTimerRunning) Localization.getString("pause", language) else Localization.getString("resume", language),
                                color = Color.White
                            )
                        }
                    }
                }

                // Discussion Prompt Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, GlassBorder),
                    colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💬 " + Localization.getString("discussion_phase", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = GoldYellow
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = Localization.getString("discussion_instruction", language),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.85f),
                                textAlign = TextAlign.Center,
                                lineHeight = 22.sp
                            )
                        )
                    }
                }

                // Proceed to Vote Button (Can vote even before timer ends)
                Button(
                    onClick = onVoteNow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .testTag("vote_now_button"),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryNeonPink)
                ) {
                    Icon(
                        imageVector = Icons.Default.HowToVote,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Localization.getString("proceed_to_vote", language),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}
