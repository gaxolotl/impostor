package com.example.data.db

import com.example.data.model.Category
import com.example.data.model.DefaultCategories
import com.example.data.model.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(private val dao: GameDao) {

    val playerStats: Flow<List<PlayerStatsEntity>> = dao.getAllPlayerStats()

    fun getCustomCategories(language: Language): Flow<List<Category>> {
        return dao.getAllCustomCategories().map { list ->
            list.filter { it.language == language.code || it.language == "all" }.map { entity ->
                val words = entity.wordsJson.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                Category(
                    id = "custom_${entity.id}",
                    nameEn = entity.name,
                    nameBg = entity.name,
                    wordsEn = words,
                    wordsBg = words,
                    iconName = "extension",
                    isCustom = true,
                    dbId = entity.id,
                    languageCode = entity.language
                )
            }
        }
    }

    suspend fun savePlayerGameResult(
        playerName: String,
        isImpostor: Boolean,
        wonGame: Boolean,
        pointsEarned: Int,
        correctVoteCast: Boolean
    ) {
        val existing = dao.getPlayerStatsByName(playerName)
        val updated = if (existing != null) {
            existing.copy(
                gamesPlayed = existing.gamesPlayed + 1,
                impostorWins = existing.impostorWins + (if (isImpostor && wonGame) 1 else 0),
                civilianWins = existing.civilianWins + (if (!isImpostor && wonGame) 1 else 0),
                totalPoints = existing.totalPoints + pointsEarned,
                timesImpostor = existing.timesImpostor + (if (isImpostor) 1 else 0),
                timesCivilian = existing.timesCivilian + (if (!isImpostor) 1 else 0),
                correctVotes = existing.correctVotes + (if (correctVoteCast) 1 else 0)
            )
        } else {
            PlayerStatsEntity(
                name = playerName,
                gamesPlayed = 1,
                impostorWins = if (isImpostor && wonGame) 1 else 0,
                civilianWins = if (!isImpostor && wonGame) 1 else 0,
                totalPoints = pointsEarned,
                timesImpostor = if (isImpostor) 1 else 0,
                timesCivilian = if (!isImpostor) 1 else 0,
                correctVotes = if (correctVoteCast) 1 else 0
            )
        }
        dao.insertOrUpdatePlayerStats(updated)
    }

    suspend fun addCustomCategory(name: String, language: Language, words: List<String>): Long {
        val wordsString = words.joinToString(",")
        val entity = CustomCategoryEntity(
            name = name,
            language = language.code,
            wordsJson = wordsString
        )
        return dao.insertCustomCategory(entity)
    }

    suspend fun deleteCustomCategory(dbId: Long) {
        dao.deleteCustomCategory(dbId)
    }

    suspend fun deletePlayer(id: Long) {
        dao.deletePlayerStats(id)
    }
}
