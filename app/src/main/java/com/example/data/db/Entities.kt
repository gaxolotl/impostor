package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_stats")
data class PlayerStatsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val gamesPlayed: Int = 0,
    val impostorWins: Int = 0,
    val civilianWins: Int = 0,
    val totalPoints: Int = 0,
    val timesImpostor: Int = 0,
    val timesCivilian: Int = 0,
    val correctVotes: Int = 0
)

@Entity(tableName = "custom_categories")
data class CustomCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val language: String, // "en" or "bg"
    val wordsJson: String, // Comma separated or json array
    val createdAt: Long = System.currentTimeMillis()
)
