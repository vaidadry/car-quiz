package com.dryzaite.carquiz.stats.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GameStatsDao {
    @Query("SELECT * FROM game_stats WHERE id = 0 LIMIT 1")
    fun observe(): Flow<GameStatsEntity?>

    @Query("SELECT * FROM game_stats WHERE id = 0 LIMIT 1")
    suspend fun get(): GameStatsEntity?

    @Upsert
    suspend fun upsert(entity: GameStatsEntity)
}
