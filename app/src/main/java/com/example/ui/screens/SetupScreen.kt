package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.DefaultCategories
import com.example.data.model.GameSettings
import com.example.data.model.Language
import com.example.data.model.Localization
import com.example.data.model.Player
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
import com.example.ui.theme.SurfaceVariantDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SetupScreen(
    language: Language,
    players: List<Player>,
    settings: GameSettings,
    customCategories: List<Category>,
    onToggleLanguage: (Language) -> Unit,
    onAddPlayer: (String) -> Unit,
    onRemovePlayer: (Int) -> Unit,
    onUpdatePlayerName: (Int, String) -> Unit,
    onUpdateSettings: (GameSettings) -> Unit,
    onStartGame: () -> Unit,
    onBack: () -> Unit
) {
    var newPlayerName by remember { mutableStateOf("") }
    val allCategories = remember(customCategories) { DefaultCategories.list + customCategories }

    val timerOptions = listOf(0, 30, 60, 90, 120, 180)

    FrostedBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            GameHeader(
                title = Localization.getString("setup_game", language),
                language = language,
                onToggleLanguage = onToggleLanguage,
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // 1. Players Section
                Text(
                    text = String.format(Localization.getString("players_count", language), players.size),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldYellow
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Player chips & add player input
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    players.forEachIndexed { index, player ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(SurfaceGlass)
                                .border(1.dp, GlassBorder, RoundedCornerShape(14.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = PrimaryNeonViolet,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = player.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                if (players.size > 3) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = ImpostorRed,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable { onRemovePlayer(index) }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Add player row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newPlayerName,
                        onValueChange = { newPlayerName = it },
                        placeholder = {
                            Text(
                                text = Localization.getString("enter_player_name", language),
                                color = Color.White.copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("add_player_input"),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceVariantDark,
                            unfocusedContainerColor = SurfaceVariantDark,
                            focusedBorderColor = PrimaryNeonViolet,
                            unfocusedBorderColor = GlassBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (newPlayerName.isNotBlank()) {
                                onAddPlayer(newPlayerName)
                                newPlayerName = ""
                            }
                        },
                        modifier = Modifier
                            .height(54.dp)
                            .testTag("add_player_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryNeonViolet)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 2. Impostors Count & Timer Settings Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, GlassBorder),
                    colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        // Impostors selector
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = Localization.getString("impostors_count", language),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }

                            val maxImpostors = (players.size - 1).coerceAtLeast(1)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = {
                                        if (settings.impostorCount > 1) {
                                            onUpdateSettings(settings.copy(impostorCount = settings.impostorCount - 1))
                                        }
                                    },
                                    enabled = settings.impostorCount > 1
                                ) {
                                    Text(
                                        text = "-",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (settings.impostorCount > 1) ImpostorRed else Color.Gray
                                    )
                                }

                                Text(
                                    text = "${settings.impostorCount}",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = ImpostorRed
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )

                                IconButton(
                                    onClick = {
                                        if (settings.impostorCount < maxImpostors) {
                                            onUpdateSettings(settings.copy(impostorCount = settings.impostorCount + 1))
                                        }
                                    },
                                    enabled = settings.impostorCount < maxImpostors
                                ) {
                                    Text(
                                        text = "+",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (settings.impostorCount < maxImpostors) MintGreen else Color.Gray
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Timer option chips
                        Text(
                            text = Localization.getString("time_limit", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            timerOptions.forEach { sec ->
                                val isSelected = settings.timeLimitSeconds == sec
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onUpdateSettings(settings.copy(timeLimitSeconds = sec)) },
                                    label = {
                                        Text(
                                            text = if (sec == 0)
                                                Localization.getString("time_unlimited", language)
                                            else
                                                String.format(Localization.getString("time_seconds", language), sec)
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SecondaryNeonPink,
                                        selectedLabelColor = Color.White,
                                        containerColor = SurfaceVariantDark,
                                        labelColor = Color.White.copy(alpha = 0.8f)
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Impostor Category Hint Toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = Localization.getString("impostor_hint", language),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = Localization.getString("impostor_hint_desc", language),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.6f)
                                    )
                                )
                            }

                            Switch(
                                checked = settings.showHintToImpostor,
                                onCheckedChange = {
                                    onUpdateSettings(settings.copy(showHintToImpostor = it))
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = MintGreen,
                                    uncheckedThumbColor = Color.Gray,
                                    uncheckedTrackColor = SurfaceVariantDark
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Category Selection Grid
                Text(
                    text = Localization.getString("select_categories", language),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldYellow
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    allCategories.forEach { category ->
                        val isSelected = settings.selectedCategoryIds.contains(category.id)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                val newSet = settings.selectedCategoryIds.toMutableSet()
                                if (isSelected) {
                                    if (newSet.size > 1) newSet.remove(category.id)
                                } else {
                                    newSet.add(category.id)
                                }
                                onUpdateSettings(settings.copy(selectedCategoryIds = newSet))
                            },
                            label = {
                                Text(text = category.getName(language))
                            },
                            leadingIcon = {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryNeonViolet,
                                selectedLabelColor = Color.White,
                                containerColor = SurfaceGlass,
                                labelColor = Color.White.copy(alpha = 0.8f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Start Game CTA Button
                Button(
                    onClick = onStartGame,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .testTag("start_game_button"),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MintGreen)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Localization.getString("start_round", language),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            }
        }
    }
}
