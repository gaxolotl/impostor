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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.data.model.Language
import com.example.data.model.Localization
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

@Composable
fun CategoriesScreen(
    language: Language,
    customCategories: List<Category>,
    onToggleLanguage: (Language) -> Unit,
    onAddCustomCategory: (title: String, words: List<String>) -> Unit,
    onDeleteCustomCategory: (dbId: Long) -> Unit,
    onBack: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var categoryTitleInput by remember { mutableStateOf("") }
    var categoryWordsInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    FrostedBackground {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                GameHeader(
                    title = Localization.getString("manage_categories", language),
                    language = language,
                    onToggleLanguage = onToggleLanguage,
                    onBack = onBack
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Built-in categories
                    item {
                        Text(
                            text = Localization.getString("built_in_categories", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = GoldYellow
                            )
                        )
                    }

                    items(DefaultCategories.list) { category ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, GlassBorder),
                            colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(PrimaryNeonViolet.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Category,
                                            contentDescription = null,
                                            tint = PrimaryNeonViolet
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = category.getName(language),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = category.getWords(language).take(8).joinToString(", ") + "...",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.6f)
                                    )
                                )
                            }
                        }
                    }

                    // Custom categories
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = Localization.getString("your_custom_categories", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SecondaryNeonPink
                            )
                        )
                    }

                    if (customCategories.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, GlassBorder),
                                colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                            ) {
                                Text(
                                    text = Localization.getString("no_custom_categories", language),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.White.copy(alpha = 0.6f)
                                    ),
                                    modifier = Modifier.padding(20.dp)
                                )
                            }
                        }
                    } else {
                        items(customCategories) { category ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, GlassBorder),
                                colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = category.getName(language),
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = category.wordsEn.joinToString(", "),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = Color.White.copy(alpha = 0.6f)
                                            )
                                        )
                                    }

                                    IconButton(
                                        onClick = { onDeleteCustomCategory(category.dbId) }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = ImpostorRed
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }

            // FAB to add category
            FloatingActionButton(
                onClick = {
                    categoryTitleInput = ""
                    categoryWordsInput = ""
                    errorMessage = null
                    showAddDialog = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
                    .testTag("add_category_fab"),
                containerColor = MintGreen,
                contentColor = Color.Black
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Category")
            }

            // Add Category Dialog
            if (showAddDialog) {
                AlertDialog(
                    onDismissRequest = { showAddDialog = false },
                    containerColor = SurfaceVariantDark,
                    title = {
                        Text(
                            text = Localization.getString("add_category", language),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = categoryTitleInput,
                                onValueChange = { categoryTitleInput = it },
                                label = { Text(Localization.getString("category_name_label", language)) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryNeonViolet,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth().testTag("custom_cat_title_input")
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = categoryWordsInput,
                                onValueChange = { categoryWordsInput = it },
                                label = { Text(Localization.getString("words_label", language)) },
                                placeholder = { Text(Localization.getString("words_hint", language)) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryNeonViolet,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("custom_cat_words_input")
                            )

                            if (errorMessage != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = errorMessage!!,
                                    color = ImpostorRed,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                val title = categoryTitleInput.trim()
                                val words = categoryWordsInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                                if (title.isEmpty()) {
                                    errorMessage = Localization.getString("enter_cat_title", language)
                                    return@Button
                                }
                                if (words.size < 3) {
                                    errorMessage = Localization.getString("enter_min_words", language)
                                    return@Button
                                }
                                onAddCustomCategory(title, words)
                                showAddDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MintGreen)
                        ) {
                            Text(Localization.getString("save", language), color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAddDialog = false }) {
                            Text(Localization.getString("cancel", language), color = Color.White)
                        }
                    }
                )
            }
        }
    }
}
