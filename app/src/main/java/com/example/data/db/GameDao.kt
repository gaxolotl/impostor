package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM player_stats ORDER BY totalPoints DESC, gamesPlayed DESC")
    fun getAllPlayerStats(): Flow<List<PlayerStatsEntity>>

    @Query("SELECT * FROM player_stats WHERE name = :name LIMIT 1")
    suspend fun getPlayerStatsByName(name: String): PlayerStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePlayerStats(stats: PlayerStatsEntity): Long

    @Query("DELETE FROM player_stats WHERE id = :id")
    suspend fun deletePlayerStats(id: Long)

    @Query("SELECT * FROM custom_categories ORDER BY createdAt DESC")
    fun getAllCustomCategories(): Flow<List<CustomCategoryEntity>>

    @Query("SELECT * FROM custom_categories WHERE language = :lang ORDER BY createdAt DESC")
    fun getCustomCategoriesByLanguage(lang: String): Flow<List<CustomCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomCategory(category: CustomCategoryEntity): Long

    @Query("DELETE FROM custom_categories WHERE id = :id")
    suspend fun deleteCustomCategory(id: Long)
}
