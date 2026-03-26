package com.dryzaite.carquiz.stats.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GameStatsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameStatsDao(): GameStatsDao
}
