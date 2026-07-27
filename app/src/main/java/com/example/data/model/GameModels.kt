package com.example.data.model

enum class Language(val code: String, val displayName: String, val flagEmoji: String) {
    EN("en", "English", "🇬🇧"),
    BG("bg", "Български", "🇧🇬")
}

enum class PlayerRole {
    CIVILIAN,
    IMPOSTOR
}

data class Player(
    val id: String,
    val name: String,
    val role: PlayerRole = PlayerRole.CIVILIAN,
    val secretWord: String = "",
    val hasSeenWord: Boolean = false
)

data class Category(
    val id: String,
    val nameEn: String,
    val nameBg: String,
    val wordsEn: List<String>,
    val wordsBg: List<String>,
    val iconName: String = "category",
    val isCustom: Boolean = false,
    val dbId: Long = 0L,
    val languageCode: String? = null
) {
    fun getName(lang: Language): String {
        return if (isCustom && !languageCode.isNullOrEmpty()) {
            nameEn
        } else {
            when (lang) {
                Language.EN -> nameEn
                Language.BG -> nameBg
            }
        }
    }

    fun getWords(lang: Language): List<String> {
        return if (isCustom) {
            wordsEn
        } else {
            when (lang) {
                Language.EN -> wordsEn
                Language.BG -> wordsBg
            }
        }
    }
}

enum class AppThemeColor(val labelEn: String, val labelBg: String, val primaryHex: Long, val secondaryHex: Long) {
    VIOLET("Violet", "Виолетов", 0xFF9333EA, 0xFFEC4899),
    EMERALD("Emerald", "Изумруд", 0xFF10B981, 0xFF06B6D4),
    SAPPHIRE("Sapphire", "Сапфир", 0xFF2563EB, 0xFF3B82F6),
    SUNSET("Sunset", "Залез", 0xFFF59E0B, 0xFFEF4444),
    ROSE("Rose", "Розов", 0xFFE11D48, 0xFFF43F5E),
    MONOCHROME("Obsidian", "Обсидиан", 0xFF475569, 0xFF64748B)
}

enum class ThemeMode(val labelEn: String, val labelBg: String) {
    DARK("Dark", "Тъмна"),
    LIGHT("Light", "Светла"),
    SYSTEM("System", "Система")
}

data class GameSettings(
    val impostorCount: Int = 1,
    val timeLimitSeconds: Int = 60, // 0 = unlimited
    val showHintToImpostor: Boolean = true,
    val selectedCategoryIds: Set<String> = setOf("brands", "foods", "animals"),
    val themeColor: AppThemeColor = AppThemeColor.VIOLET,
    val themeMode: ThemeMode = ThemeMode.DARK
)

enum class GamePhase {
    HOME,
    SETUP,
    REVEAL,
    DISCUSSION,
    VOTING,
    RESULT,
    CATEGORIES,
    LEADERBOARD
}

data class VoteRecord(
    val voterPlayerId: String,
    val votedPlayerId: String
)

data class RoundResult(
    val secretWord: String,
    val categoryName: String,
    val impostorNames: List<String>,
    val votedOutNames: List<String>,
    val isImpostorCaught: Boolean,
    val winnerTeam: PlayerRole,
    val pointsAwarded: Map<String, Int>
)
