package com.dryzaite.carquiz.stats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_stats")
data class GameStatsEntity(
    @PrimaryKey val id: Int = 0,
    val lastQuizCorrect: Int = 0,
    val lastQuizTotal: Int = 0,
    val lastFlashcardsGuessed: Int = 0,
    val lastFlashcardsTotal: Int = 0,
    val bestQuizCorrect: Int = 0,
    val bestQuizTotal: Int = 0,
    val bestFlashcardsGuessed: Int = 0,
    val bestFlashcardsTotal: Int = 0
)
