package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Language
import com.example.data.model.Localization
import com.example.data.model.Player
import com.example.data.model.PlayerRole
import com.example.ui.theme.CivilianBlue
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.ImpostorRed
import com.example.ui.theme.MintGreen
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryNeonViolet
import com.example.ui.theme.SecondaryNeonPink
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceGlass
import kotlin.math.roundToInt

@Composable
fun SwipeRevealCard(
    player: Player,
    showHint: Boolean,
    categoryName: String,
    language: Language,
    onConfirmSeen: () -> Unit
) {
    var offsetY by remember(player.id) { mutableFloatStateOf(0f) }
    var hasRevealedFully by remember(player.id) { mutableStateOf(false) }

    val isImpostor = player.role == PlayerRole.IMPOSTOR

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
            .testTag("swipe_reveal_card"),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, GlassBorder),
        colors = CardDefaults.cardColors(containerColor = SurfaceGlass),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Secret Content Layer (Underneath Cover)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                if (isImpostor) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(ImpostorRed.copy(alpha = 0.2f))
                            .border(1.dp, ImpostorRed.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🤫", fontSize = 36.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = Localization.getString("you_are_impostor", language),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = ImpostorRed,
                            letterSpacing = 1.2.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = Localization.getString("impostor_subtext", language),
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.8f)),
                        textAlign = TextAlign.Center
                    )

                    if (showHint && categoryName.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(GoldYellow.copy(alpha = 0.15f))
                                .border(1.dp, GoldYellow.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = String.format(
                                    Localization.getString("impostor_category_hint", language),
                                    categoryName
                                ),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = GoldYellow,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(CivilianBlue.copy(alpha = 0.2f))
                            .border(1.dp, CivilianBlue.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Secret Word",
                            tint = CivilianBlue,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = Localization.getString("civilian_secret_word", language),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = CivilianBlue,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(PrimaryNeonViolet.copy(alpha = 0.35f), SecondaryNeonPink.copy(alpha = 0.35f))
                                )
                            )
                            .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                            .padding(vertical = 18.dp, horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = player.secretWord.ifEmpty { "..." },
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = Localization.getString("civilian_subtext", language),
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.7f)),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(
                    visible = hasRevealedFully || offsetY < -100f,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = onConfirmSeen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("confirm_seen_button"),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MintGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = Localization.getString("confirm_seen", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Cover Layer (Draggable Cover Card)
            if (!hasRevealedFully) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { IntOffset(0, offsetY.roundToInt()) }
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(SurfaceDark, PrimaryNeonViolet.copy(alpha = 0.85f), PrimaryIndigo.copy(alpha = 0.9f))
                            )
                        )
                        .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
                        .pointerInput(player.id) {
                            detectVerticalDragGestures(
                                onDragEnd = {
                                    if (offsetY < -180f) {
                                        offsetY = -360f
                                        hasRevealedFully = true
                                    } else {
                                        offsetY = 0f
                                    }
                                },
                                onVerticalDrag = { change, dragAmount ->
                                    change.consume()
                                    val newOffset = (offsetY + dragAmount).coerceAtMost(0f)
                                    offsetY = newOffset
                                    if (offsetY < -220f) {
                                        hasRevealedFully = true
                                    }
                                }
                            )
                        }
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Secret",
                            tint = GoldYellow,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = player.name,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = "Swipe up",
                            tint = MintGreen,
                            modifier = Modifier.size(36.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = Localization.getString("swipe_up_hint", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MintGreen,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
